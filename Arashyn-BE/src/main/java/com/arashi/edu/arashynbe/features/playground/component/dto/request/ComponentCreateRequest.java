package com.arashi.edu.arashynbe.features.playground.component.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ComponentCreateRequest(
        @NotNull
        @Positive
        Integer order,

        @NotNull
        @Positive
        Integer groupKey,

        UUID formId,

        String keyWord,

        @NotNull
        boolean optional
) {
}