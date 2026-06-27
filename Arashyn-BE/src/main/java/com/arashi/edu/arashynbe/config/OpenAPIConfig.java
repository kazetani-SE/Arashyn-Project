package com.arashi.edu.arashynbe.config;

import com.arashi.edu.arashynbe.config.properties.OpenAPIProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfig {

  /**
   * Security scheme name referenced by SecurityRequirement.
   */
  private static final String SECURITY_SCHEME = "BearerAuth";

  private final OpenAPIProperties properties;

  @Bean
  public OpenAPI openAPI() {

    var info = new Info()
            .title(properties.getTitle())
            .description(properties.getDescription())
            .version(properties.getVersion())
            .contact(new Contact()
                    .name(properties.getContactName())
                    .email(properties.getContactEmail()));

    var bearerScheme = new SecurityScheme()
            // Use HTTP Bearer authentication for JWT tokens.
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

    return new OpenAPI()
            .info(info)
            // Apply Bearer authentication globally.
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
            .components(new Components()
                    .addSecuritySchemes(SECURITY_SCHEME, bearerScheme));
  }
}