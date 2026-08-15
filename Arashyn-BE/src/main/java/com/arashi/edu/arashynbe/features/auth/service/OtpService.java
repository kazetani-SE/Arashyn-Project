package com.arashi.edu.arashynbe.features.auth.service;

import com.arashi.edu.arashynbe.features.auth.dto.GeneratedOtp;

public interface OtpService {

  GeneratedOtp generateCode(String email);

  boolean matches(String rawCode, String hashedCode);

  int getExpirationTime();

  int getResendCooldown();
}