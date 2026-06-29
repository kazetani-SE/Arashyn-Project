package com.arashi.edu.arashynbe.features.playground.grammar.dto.request;

import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record GrammarCreateRequest(
        @NotBlank
        @Size(min = 1, max = 50)
        String title,

        @NotNull
        Language language,

        @NotNull
        boolean isPublic,

        @NotEmpty
        List<@Valid ComponentCreateRequest> components
) {
}