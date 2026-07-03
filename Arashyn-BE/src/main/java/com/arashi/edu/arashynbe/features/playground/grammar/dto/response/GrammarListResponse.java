package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import java.util.List;

public record GrammarListResponse(

        List<GrammarSummaryResponse> grammars,

        Integer page,

        Integer size,

        Integer totalPages,

        Long totalElements,

        Boolean hasNext,

        Boolean hasPrevious

) {
}