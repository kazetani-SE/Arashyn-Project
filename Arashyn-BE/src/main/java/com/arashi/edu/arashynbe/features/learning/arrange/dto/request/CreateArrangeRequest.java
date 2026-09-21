package com.arashi.edu.arashynbe.features.learning.arrange.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateArrangeRequest(

        @NotNull
        UUID userDeckId,

        @NotNull
        @Positive
        Integer numOfQuestion

) {
}