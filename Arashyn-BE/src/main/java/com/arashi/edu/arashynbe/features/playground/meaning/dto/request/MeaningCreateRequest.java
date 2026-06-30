package com.arashi.edu.arashynbe.features.playground.meaning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MeaningCreateRequest(

        @NotBlank
        @Size(max = 5000)
        String content,

        @NotNull
        @Positive
        Integer groupKey
) {
}