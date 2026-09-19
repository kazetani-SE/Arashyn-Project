package com.arashi.edu.arashynbe.features.system.grammar.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GrammarCreateMultipleRequest(

        @NotNull
        List<GrammarCreateRequest> createRequestList

) {
}