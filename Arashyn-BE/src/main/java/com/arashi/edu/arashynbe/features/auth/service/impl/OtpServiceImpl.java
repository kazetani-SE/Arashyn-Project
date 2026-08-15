package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.features.auth.repository.PendingRegistrationRepository;
import com.arashi.edu.arashynbe.features.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private final int OTP_LENGTH = 6;
  private final int EXPIRATION_TIME = 5; // minutes
  private final int RESEND_COOLDOWN = 60;  // seconds
  private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final SecureRandom secureRandom = new SecureRandom();
  private final PendingRegistrationRepository pendingRegistrationRepository;

  @Override
  public String generateCode(String email) {
    String rawCode = generateRandomCode(OTP_LENGTH);

    while(pendingRegistrationRepository.existsByVerificationCode(rawCode)) {
      rawCode = generateRandomCode(OTP_LENGTH);
    }

    return rawCode;
  }

  private String generateRandomCode(int length) {
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = secureRandom.nextInt(CODE_CHARACTERS.length());
      sb.append(CODE_CHARACTERS.charAt(index));
    }

    return sb.toString();
  }
}