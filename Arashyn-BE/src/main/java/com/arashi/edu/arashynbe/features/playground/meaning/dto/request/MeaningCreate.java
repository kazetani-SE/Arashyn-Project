package com.arashi.edu.arashynbe.features.playground.meaning.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MeaningCreate(
        @NotNull
        @Positive
        Integer groupKey,

        @NotNull
        @Valid
        MeaningCreateBase meaning
){}