package com.arashi.edu.arashynbe.config;

import com.arashi.edu.arashynbe.config.properties.SupabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class SupabaseConfig {

  private final SupabaseProperties properties;

  @Bean
  public RestClient supabaseRestClient() {

    return RestClient.builder()
            .baseUrl(properties.getUrl())
            .defaultHeader("apikey", properties.getPublishableKey())
            .build();
  }
}