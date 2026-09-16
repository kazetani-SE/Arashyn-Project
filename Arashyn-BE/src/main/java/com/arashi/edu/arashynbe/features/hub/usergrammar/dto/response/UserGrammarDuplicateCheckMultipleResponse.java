package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response;

import java.util.List;

public record UserGrammarDuplicateCheckMultipleResponse(

        List<UserGrammarDuplicateCheckResponse> duplicateCheckResponseList

) {
}