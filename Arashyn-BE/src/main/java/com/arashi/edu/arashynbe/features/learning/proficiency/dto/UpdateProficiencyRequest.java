package com.arashi.edu.arashynbe.features.learning.proficiency.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateProficiencyRequest(

        @NotNull
        UUID userGrammarId,

        @Positive
        Integer changes

) {
}