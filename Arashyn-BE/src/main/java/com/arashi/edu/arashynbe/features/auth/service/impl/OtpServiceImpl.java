package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.features.auth.dto.GeneratedOtp;
import com.arashi.edu.arashynbe.features.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private static final int OTP_LENGTH = 6;
  private static final int EXPIRATION_TIME = 5;
  private static final int RESEND_COOLDOWN = 1;

  private static final String CODE_CHARACTERS =
          "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private final SecureRandom secureRandom = new SecureRandom();
  private final BCryptPasswordEncoder passwordEncoder =
          new BCryptPasswordEncoder();

  @Override
  public GeneratedOtp generateCode(String email) {
    String rawCode = generateRandomCode(OTP_LENGTH);
    String hashedCode = passwordEncoder.encode(rawCode);

    return new GeneratedOtp(rawCode, hashedCode);
  }

  @Override
  public boolean matches(String rawCode, String hashedCode) {
    return passwordEncoder.matches(rawCode, hashedCode);
  }

  @Override
  public int getExpirationTime() {
    return EXPIRATION_TIME;
  }

  @Override
  public int getResendCooldown() {
    return RESEND_COOLDOWN;
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