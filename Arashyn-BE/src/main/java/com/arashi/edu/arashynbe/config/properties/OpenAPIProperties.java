package com.arashi.edu.arashynbe.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "openapi")
public class OpenAPIProperties {

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  @NotBlank
  private String version;

  @NotBlank
  private String contactName;
}