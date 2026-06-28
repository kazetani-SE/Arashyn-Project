package com.arashi.edu.arashynbe.config.security;

import com.arashi.edu.arashynbe.config.properties.SupabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtDecoder jwtDecoder;
  private final SupabaseProperties supabaseProperties;

  public UUID extractUserId(String token) {
    Jwt jwt = jwtDecoder.decode(token);

    validateIssuer(jwt);

    return UUID.fromString(jwt.getSubject());
  }

  private void validateIssuer(Jwt jwt) {
    String expectedIssuer = supabaseProperties.getUrl() + "/auth/v1";

    if (!expectedIssuer.equals(jwt.getIssuer().toString())) {
      throw new IllegalArgumentException("Invalid JWT issuer.");
    }
  }
}