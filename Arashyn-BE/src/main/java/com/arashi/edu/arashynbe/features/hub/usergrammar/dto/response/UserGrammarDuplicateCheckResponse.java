package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response;

import java.util.UUID;

public record UserGrammarDuplicateCheckResponse(

        UUID id,

        boolean duplicated,

        UUID existingUserGrammarId

) {
}