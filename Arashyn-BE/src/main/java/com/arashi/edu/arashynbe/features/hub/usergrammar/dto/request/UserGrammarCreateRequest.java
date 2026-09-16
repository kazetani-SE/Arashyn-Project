package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserGrammarCreateRequest(

        UUID grammarId,

        String name,

        @NotNull
        UUID userDeckId,

        UUID sourceUserGrammarId

) {
}