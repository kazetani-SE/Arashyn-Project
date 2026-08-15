package com.arashi.edu.arashynbe.features.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationRequest(
        @Email
        @NotBlank
        String email
) {
}