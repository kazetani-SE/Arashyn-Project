package com.arashi.edu.arashynbe.config.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

}