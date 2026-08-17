package com.arashi.edu.arashynbe.features.auth.controller;

import com.arashi.edu.arashynbe.features.auth.dto.request.CompleteRegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterVerifyRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.ResendVerificationRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.VerifyOtpRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.LoginResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.features.auth.service.RegistrationOtpService;
import com.arashi.edu.arashynbe.features.auth.support.CookieUtil;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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
          @Valid @RequestBody LoginRequest request,
          HttpServletResponse httpResponse
  ) {
    var result = authService.login(request);

    httpResponse.addHeader(HttpHeaders.SET_COOKIE,
            CookieUtil.refreshTokenCookie(result.refreshToken(), Duration.ofDays(30)).toString());

    return ResponseEntity.ok(new LoginResponse(result.username(), result.avatar(), result.accessToken()));
  }

  @PostMapping("/refresh")
  public ResponseEntity<LoginResponse> refresh(
          @CookieValue(name = "refresh_token", required = false) String refreshToken,
          HttpServletResponse httpResponse
  ) {
    try{
      var result = authService.refresh(refreshToken);

      httpResponse.addHeader(HttpHeaders.SET_COOKIE,
              CookieUtil.refreshTokenCookie(result.refreshToken(), Duration.ofDays(30)).toString());

      return ResponseEntity.ok(new LoginResponse(result.username(), result.avatar(), result.accessToken()));
    }catch (ApiException e) {
      if (e.getErrorCode() == ErrorCode.SESSION_EXPIRED) {
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.expiredRefreshTokenCookie().toString());
      }
      throw e;
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
          @CookieValue(name = "refresh_token", required = false) String refreshToken,
          HttpServletResponse res
  ) {
    authService.logout(refreshToken);

    res.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.expiredRefreshTokenCookie().toString());

    return ResponseEntity.noContent().build();
  }
}