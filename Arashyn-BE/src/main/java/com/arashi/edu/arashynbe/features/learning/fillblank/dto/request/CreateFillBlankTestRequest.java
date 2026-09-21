package com.arashi.edu.arashynbe.features.learning.fillblank.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Difficulty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateFillBlankTestRequest(

        @NotNull
        UUID userDeckId,

        @NotNull
        Difficulty difficulty,

        @NotNull
        @Positive
        Integer numOfQuestion,

        @NotNull
        boolean hasForm,

        @NotNull
        boolean hasKeyword

) {
}