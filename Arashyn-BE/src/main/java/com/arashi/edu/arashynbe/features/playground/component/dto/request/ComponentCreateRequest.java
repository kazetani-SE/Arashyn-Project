package com.arashi.edu.arashynbe.features.playground.component.dto.request;

import com.arashi.edu.arashynbe.shared.validator.ExactlyOneOfFormOrKeyword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@ExactlyOneOfFormOrKeyword
public record ComponentCreateRequest(
        @NotNull
        @Positive
        Integer order,

        UUID formId,

        String keyWord,

        @NotNull
        boolean optional
) {
}