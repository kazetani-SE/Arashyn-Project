package com.arashi.edu.arashynbe.config;

import com.arashi.edu.arashynbe.config.properties.SupabaseProperties;
import com.arashi.edu.arashynbe.config.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
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

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http)
          throws Exception {

    http
            .csrf(AbstractHttpConfigurer::disable)

            .cors(Customizer.withDefaults())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, e) ->
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                    .accessDeniedHandler((req, res, e) ->
                            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
            )

            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/auth/**",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                    ).permitAll()

                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                    .anyRequest()
                    .authenticated())

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}