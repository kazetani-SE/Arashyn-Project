package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserGrammarDuplicateCheckRequest(

        @NotNull
        UUID id

) {
}