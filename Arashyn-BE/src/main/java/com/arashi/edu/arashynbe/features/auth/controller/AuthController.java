package com.arashi.edu.arashynbe.features.auth.controller;

import com.arashi.edu.arashynbe.features.auth.dto.request.CompleteRegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RefreshRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterVerifyRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.ResendVerificationRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.VerifyOtpRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.LoginResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.features.auth.service.RegistrationOtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final RegistrationOtpService registrationOtpService;

  @PostMapping("/verify")
  public ResponseEntity<Void> initiateRegistration(
          @Valid @RequestBody RegisterVerifyRequest payload
  ) {
    var result = registrationOtpService.initiate(payload);

    return switch (result) {
      case EMAIL_ALREADY_EXISTS ->
              ResponseEntity.status(HttpStatus.CONFLICT).build();

      case VERIFICATION_EMAIL_SENT ->
              ResponseEntity.accepted().build();
    };
  }

  @PostMapping("/verify/confirm")
  public ResponseEntity<Void> verifyRegistration(
          @Valid @RequestBody VerifyOtpRequest request
  ) {
    registrationOtpService.verify(
            request.email(),
            request.code()
    );

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/verify/resend")
  public ResponseEntity<Void> resendVerification(
          @Valid @RequestBody ResendVerificationRequest request
  ) {
    registrationOtpService.resend(request.email());

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/complete-register")
  public ResponseEntity<RegisterResponse> completeRegister(
          @Valid @RequestBody CompleteRegisterRequest request
  ) {
    return ResponseEntity.ok(
            registrationOtpService.complete(
                    request.email(),
                    request.password()
            )
    );
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
          @Valid @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(
            authService.register(request)
    );
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
          @Valid @RequestBody LoginRequest request
  ) {
    return ResponseEntity.ok(
            authService.login(request)
    );
  }

  @PostMapping("/refresh")
  public LoginResponse refresh(@RequestBody RefreshRequest request) {
    return authService.refresh(request);
  }
}