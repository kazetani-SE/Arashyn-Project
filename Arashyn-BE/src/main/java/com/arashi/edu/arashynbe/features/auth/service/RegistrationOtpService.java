package com.arashi.edu.arashynbe.features.auth.service;

import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterVerifyRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegistrationInitiateResult;

public interface RegistrationOtpService {
  RegistrationInitiateResult initiate(RegisterVerifyRequest payload);
  void verify(String email, String code);
  void resend(String email);
}