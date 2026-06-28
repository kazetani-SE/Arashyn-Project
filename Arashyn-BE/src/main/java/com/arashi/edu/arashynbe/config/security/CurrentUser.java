package com.arashi.edu.arashynbe.config.security;

import com.arashi.edu.arashynbe.shared.enums.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Utility class for retrieving the current authenticated SecurityUser
 * from Spring Security's SecurityContext.
 */
public final class CurrentUser {

  private CurrentUser() {
  }

  public static SecurityUser get() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
            || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken
            || !(authentication.getPrincipal() instanceof SecurityUser user)) {
      return null;
    }

    return user;
  }

  public static UUID getId() {
    SecurityUser user = get();
    return user != null ? user.id() : null;
  }

  public static Role getRole() {
    SecurityUser user = get();
    return user != null ? user.role() : null;
  }

  public static boolean isAuthenticated() {
    return get() != null;
  }
}