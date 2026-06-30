package com.arashi.edu.arashynbe.features.playground.form.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String type,

        @NotNull
        Language language
) {
}