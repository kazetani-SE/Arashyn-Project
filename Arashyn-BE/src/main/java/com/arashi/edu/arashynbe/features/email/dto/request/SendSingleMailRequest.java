package com.arashi.edu.arashynbe.features.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendSingleMailRequest(
        @NotBlank
        @Email
        String toEmail,

        @NotBlank
        String subject,

        @NotBlank
        String body
) {
}