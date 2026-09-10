package com.arashi.edu.arashynbe.features.hub.userdeck.dto.response;

import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record UserDeckDetailResponse(

        UUID id,

        UUID deckId,

        String name,

        String description,

        Language language,

        Set<GrammarSummaryResponse> grammars,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt

) {
}