package com.arashi.edu.arashynbe.features.hub.userdeck.dto.response;

import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarListResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserDeckDetailResponse(

        UUID id,

        UUID deckId,

        String name,

        String description,

        Language language,

        OffsetDateTime lastOpenAt,

        UserGrammarListResponse grammars,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt

) {
}