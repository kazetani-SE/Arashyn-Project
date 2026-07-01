package com.arashi.edu.arashynbe.features.playground.filter.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SystemFilterCreateRequest(

        @NotBlank
        @Size(max = 50)
        String name,

        @NotNull
        Language language

) {
}