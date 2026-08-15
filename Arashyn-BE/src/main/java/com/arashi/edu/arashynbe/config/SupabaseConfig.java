package com.arashi.edu.arashynbe.config;

import com.arashi.edu.arashynbe.config.properties.SupabaseProperties;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.net.URL;

@Configuration
@RequiredArgsConstructor
public class SupabaseConfig {

  private final SupabaseProperties properties;

  @Bean
  public RestClient supabaseRestClient() {

    return RestClient.builder()
            .baseUrl(properties.getUrl())
            .defaultHeader("apikey", properties.getPublishableKey())
            .defaultHeader("Authorization", "Bearer " + properties.getPublishableKey())
            .build();
  }

  @Bean
  public JWKSource<SecurityContext> supabaseJwkSource() throws Exception {
    URL jwksUrl = new URL(properties.getUrl() + "/auth/v1/.well-known/jwks.json");
    return new RemoteJWKSet<>(jwksUrl);
  }
}