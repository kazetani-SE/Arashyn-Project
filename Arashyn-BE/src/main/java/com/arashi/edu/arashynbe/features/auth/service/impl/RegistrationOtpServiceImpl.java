package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterVerifyRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegistrationInitiateResult;
import com.arashi.edu.arashynbe.features.auth.service.RegistrationOtpService;
import com.arashi.edu.arashynbe.features.email.service.EmailService;
import com.arashi.edu.arashynbe.features.auth.service.OtpService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationOtpServiceImpl implements RegistrationOtpService {

  private final AccountRepo accountRepo;
  private final EmailService emailService;
  private final OtpService otpService;

  // change the void to the other that is more suitable for controller to detect the email exist, internal error and success
  @Override
  public RegistrationInitiateResult initiate(RegisterVerifyRequest payload) {
    if (accountRepo.existsByEmail(payload.email())) {
      return RegistrationInitiateResult.EMAIL_ALREADY_EXISTS;
    }

    var newOtp = otpService.generateCode(payload.email());

    emailService.sendVerificationEmail(payload.email(), newOtp);

    return RegistrationInitiateResult.VERIFICATION_EMAIL_SENT;
  }

  @Override
  public void verify(String email, String code) {

  }

  @Override
  public void resend(String email) {

  }
}