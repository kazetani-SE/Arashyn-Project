package com.arashi.edu.arashynbe.config.security;

import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.shared.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtService jwtService;
  private final AccountRepo accountRepository;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(BEARER_PREFIX.length());

    try {
      UUID userId = jwtService.extractUserId(token);

      Role role = accountRepository.findRoleById(userId)
              .orElseThrow();

      var securityUser = new SecurityUser(userId, role);

      var authentication =
              new UsernamePasswordAuthenticationToken(
                      securityUser,
                      null,
                      List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
              );

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
      log.warn("JWT authentication failed: " + ex.getMessage());
    }

    filterChain.doFilter(request, response);
  }
}