package com.arashi.edu.arashynbe.config;


import com.arashi.edu.arashynbe.config.properties.SupabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

  private final SupabaseProperties supabaseProperties;

  @Bean
  JwtDecoder jwtDecoder() {

    String issuer = supabaseProperties.getUrl() + "/auth/v1";

    NimbusJwtDecoder decoder =
            NimbusJwtDecoder.withJwkSetUri(
                            issuer + "/.well-known/jwks.json")
                    .build();

    OAuth2TokenValidator<Jwt> validator =
            JwtValidators.createDefaultWithIssuer(issuer);

    decoder.setJwtValidator(validator);

    return decoder;
  }
}