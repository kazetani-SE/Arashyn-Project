package com.arashi.edu.arashynbe.features.auth.dto.request;

import com.arashi.edu.arashynbe.shared.validator.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        @StrongPassword
        String password,

        @NotBlank
        String userName
) {
}