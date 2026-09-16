package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response;

import com.arashi.edu.arashynbe.features.system.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.system.note.dto.response.GrammarNoteResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UserGrammarDetailResponse(

        UUID id,

        UUID grammarId,

        String title,

        Language language,

        List<GrammarDetailResponse.Group> groups,

        List<GrammarNoteResponse> notes,

        List<GrammarFilterResponse> filters,

        Short proficiency,

        OffsetDateTime lastReviewAt

) {
}