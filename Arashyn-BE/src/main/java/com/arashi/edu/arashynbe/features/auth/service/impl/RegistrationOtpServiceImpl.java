package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterVerifyRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegistrationInitiateResult;
import com.arashi.edu.arashynbe.features.auth.entity.PendingRegistration;
import com.arashi.edu.arashynbe.features.auth.repository.PendingRegistrationRepository;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.features.auth.service.OtpService;
import com.arashi.edu.arashynbe.features.auth.service.RegistrationOtpService;
import com.arashi.edu.arashynbe.features.email.service.EmailService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationOtpServiceImpl implements RegistrationOtpService {

  private static final String VERIFIED_CODE = "VERIFIED";

  private final AccountRepo accountRepo;
  private final EmailService emailService;
  private final PendingRegistrationRepository pendingRegistrationRepository;
  private final OtpService otpService;
  private final ObjectMapper objectMapper;
  private final AuthService authService;

  @Override
  public RegistrationInitiateResult initiate(RegisterVerifyRequest payload) {
    if (accountRepo.existsByEmail(payload.email())) {
      return RegistrationInitiateResult.EMAIL_ALREADY_EXISTS;
    }

    var newOtp = otpService.generateCode(payload.email());

    emailService.sendVerificationEmail(
            payload.email(),
            newOtp.rawCode()
    );

    var pendingRegistration = PendingRegistration.builder()
            .email(payload.email())
            .verificationCode(newOtp.hashedCode())
            .payload(buildPayloadJson(payload))
            .expiresAt(
                    OffsetDateTime.now()
                            .plusMinutes(otpService.getExpirationTime())
            )
            .build();

    pendingRegistrationRepository.save(pendingRegistration);

    return RegistrationInitiateResult.VERIFICATION_EMAIL_SENT;
  }

  @Override
  public void verify(String email, String code) {
    var pendingRegistration = findPendingRegistration(email);

    if (isExpired(pendingRegistration)) {
      throw new ApiException(
              ErrorCode.VERIFICATION_CODE_EXPIRED
      );
    }

    if (!otpService.matches(
            code,
            pendingRegistration.getVerificationCode()
    )) {
      throw new ApiException(
              ErrorCode.VERIFICATION_CODE_INVALID
      );
    }

    pendingRegistration.setVerificationCode(VERIFIED_CODE);

    pendingRegistrationRepository.save(pendingRegistration);
  }

  @Override
  public void resend(String email) {
    if (accountRepo.existsByEmail(email)) {
      throw new ApiException(
              ErrorCode.EMAIL_ALREADY_EXISTS
      );
    }

    var pendingRegistration = findPendingRegistration(email);

    if (!canResend(pendingRegistration)) {
      throw new ApiException(
              ErrorCode.VERIFICATION_CODE_RESEND_COOLDOWN
      );
    }

    var newOtp = otpService.generateCode(email);

    emailService.sendVerificationEmail(
            email,
            newOtp.rawCode()
    );

    pendingRegistration.setVerificationCode(
            newOtp.hashedCode()
    );

    pendingRegistration.setExpiresAt(
            OffsetDateTime.now()
                    .plusMinutes(otpService.getExpirationTime())
    );

    pendingRegistrationRepository.save(pendingRegistration);
  }

  @Override
  @Transactional
  public RegisterResponse complete(String email, String password) {
    var pendingRegistration = findPendingRegistration(email);

    if (!VERIFIED_CODE.equals(
            pendingRegistration.getVerificationCode()
    )) {
      throw new ApiException(
              ErrorCode.EMAIL_NOT_VERIFIED
      );
    }

    var registerRequest = buildRegisterRequest(
            pendingRegistration,
            password
    );

    if (accountRepo.existsByEmail(email)) {
      throw new ApiException(
              ErrorCode.EMAIL_ALREADY_EXISTS
      );
    }

    var response = authService.register(registerRequest);

    pendingRegistrationRepository.delete(pendingRegistration);

    return response;
  }

  private PendingRegistration findPendingRegistration(String email) {
    return pendingRegistrationRepository
            .findByEmail(email)
            .orElseThrow(() ->
                    new ApiException(
                            ErrorCode.PENDING_REGISTRATION_NOT_FOUND
                    )
            );
  }

  private boolean isExpired(
          PendingRegistration pendingRegistration
  ) {
    return pendingRegistration.getExpiresAt()
            .isBefore(OffsetDateTime.now());
  }

  private boolean canResend(
          PendingRegistration pendingRegistration
  ) {
    var now = OffsetDateTime.now();

    var remainingSeconds = java.time.Duration.between(
            now,
            pendingRegistration.getExpiresAt()
    ).getSeconds();

    var resendThresholdSeconds =
            (otpService.getExpirationTime() - otpService.getResendCooldown()) * 60L;

    return remainingSeconds > 0
            && remainingSeconds <= resendThresholdSeconds;
  }

  private RegisterRequest buildRegisterRequest(
          PendingRegistration pendingRegistration,
          String password
  ) {
    try {
      JsonNode jsonNode = objectMapper.readTree(
              pendingRegistration.getPayload()
      );

      if (!jsonNode.isObject()) {
        throw new ApiException(
                ErrorCode.REGISTRATION_PAYLOAD_INVALID
        );
      }

      ObjectNode objectNode = (ObjectNode) jsonNode;

      objectNode.put("password", password);

      return objectMapper.treeToValue(
              objectNode,
              RegisterRequest.class
      );

    } catch (JsonProcessingException exception) {
      throw new ApiException(
              ErrorCode.REGISTRATION_PAYLOAD_INVALID
      );
    }
  }

  private String buildPayloadJson(RegisterVerifyRequest payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException exception) {
      throw new ApiException(
              ErrorCode.REGISTRATION_PAYLOAD_INVALID
      );
    }
  }
}