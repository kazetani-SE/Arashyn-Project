package com.arashi.edu.arashynbe.config;

import com.arashi.edu.arashynbe.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

  private final CorsProperties corsProperties;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();

    config.setAllowedOrigins(corsProperties.getAllowedOrigins());

    config.setAllowedMethods(corsProperties.getAllowedMethods());

    config.setAllowedHeaders(corsProperties.getAllowedHeaders());

    config.setExposedHeaders(corsProperties.getExposedHeaders());

    config.setAllowCredentials(corsProperties.isAllowCredentials());

    if (corsProperties.getMaxAge() != null) {
      config.setMaxAge(corsProperties.getMaxAge());
    }

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}