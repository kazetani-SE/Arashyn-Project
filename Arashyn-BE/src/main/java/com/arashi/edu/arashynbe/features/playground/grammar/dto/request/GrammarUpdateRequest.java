package com.arashi.edu.arashynbe.features.playground.grammar.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GrammarUpdateRequest(

        @NotNull
        UUID oldGrammarID,

        @Valid
        GrammarCreateRequest newGrammar

) {
}