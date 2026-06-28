package com.arashi.edu.arashynbe.config.security;

import com.arashi.edu.arashynbe.shared.enums.Role;

import java.util.UUID;

/**
 * Represents the authenticated user stored in Spring Security's SecurityContext
 * for the lifetime of a single request.
 */
public record SecurityUser(
        UUID id,
        Role role
) {}