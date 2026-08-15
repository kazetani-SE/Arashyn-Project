package com.arashi.edu.arashynbe.features.email.service;

import com.arashi.edu.arashynbe.features.email.dto.request.SendSingleMailRequest;

public interface EmailService {
  
  void sendSingleMail(SendSingleMailRequest sendSingleMailRequest);
  
  void sendVerificationEmail(String to, String otpCode);
}