package com.arashi.edu.arashynbe.config.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

  @NotEmpty
  private List<String> allowedOrigins;

  @NotEmpty
  private List<String> allowedMethods;

  @NotEmpty
  private List<String> allowedHeaders;

  private List<String> exposedHeaders;

  private boolean allowCredentials;

  @Positive
  private Long maxAge;

}