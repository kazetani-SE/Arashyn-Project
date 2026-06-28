package com.arashi.edu.arashynbe.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "supabase")
public class SupabaseProperties {

  @NotBlank
  private String url;

  @NotBlank
  private String publishableKey;
}