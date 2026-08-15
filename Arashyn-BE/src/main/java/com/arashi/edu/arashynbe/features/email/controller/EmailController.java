package com.arashi.edu.arashynbe.features.email.controller;

import com.arashi.edu.arashynbe.features.email.dto.request.SendSingleMailRequest;
import com.arashi.edu.arashynbe.features.email.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  @PostMapping("/send-mail")
  public ResponseEntity<Void> sendVerificationEmail(
          @Valid @RequestBody SendSingleMailRequest sendSingleMailRequest
          ){
    emailService.sendSingleMail(sendSingleMailRequest);

    return ResponseEntity.noContent().build();
  }
}