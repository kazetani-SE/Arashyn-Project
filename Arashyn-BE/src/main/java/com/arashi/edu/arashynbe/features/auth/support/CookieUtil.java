package com.arashi.edu.arashynbe.features.auth.support;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public final class CookieUtil {
  private CookieUtil() {}

  public static ResponseCookie refreshTokenCookie(String value, Duration maxAge) {
    return ResponseCookie.from("refresh_token", value)
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .path("/auth/refresh")
            .maxAge(maxAge)
            .build();
  }

  public static ResponseCookie expiredRefreshTokenCookie() {
    return ResponseCookie.from("refresh_token", "")
            .httpOnly(true).secure(true).sameSite("Strict")
            .path("/auth/refresh").maxAge(0).build();
  }
}