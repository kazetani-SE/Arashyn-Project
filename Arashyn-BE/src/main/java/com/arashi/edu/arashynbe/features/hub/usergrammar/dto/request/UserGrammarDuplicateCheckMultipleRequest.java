package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserGrammarDuplicateCheckMultipleRequest(

        @NotNull
        List<UserGrammarDuplicateCheckRequest> duplicateCheckRequestList

) {
}