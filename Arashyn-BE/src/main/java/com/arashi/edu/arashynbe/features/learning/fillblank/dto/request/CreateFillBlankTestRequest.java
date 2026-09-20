package com.arashi.edu.arashynbe.features.learning.fillblank.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Difficulty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateFillBlankTestRequest(

        @NotNull
        UUID userDeckId,

        @NotNull
        Difficulty difficulty,

        @NotNull
        @Min(value = 5, message = "Number of questions must be at least 5")
        Integer numOfQuestion,

        @NotNull
        boolean hasForm,

        @NotNull
        boolean hasKeyword

) {
}