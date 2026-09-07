package com.arashi.edu.arashynbe.features.system.deck.dto.response;

import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderSummaryResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record DeckDetailResponse(

        UUID id,

        String name,

        String description,

        Language language,

        UUID ownerId,

        Boolean isPublic,

        Set<FolderSummaryResponse> folders,

        Set<GrammarSummaryResponse> grammars,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt
) {
}