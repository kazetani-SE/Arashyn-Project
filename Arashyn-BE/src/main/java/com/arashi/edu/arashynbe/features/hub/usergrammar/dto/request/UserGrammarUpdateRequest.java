package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserGrammarUpdateRequest(

        @NotNull
        UUID userGrammarId,

        String name,

        UUID currentUserDeckId,

        UUID newUserDeckId

) {
}