package com.arashi.edu.arashynbe.features.system.grammar.dto.response;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;

import java.util.UUID;

public record GrammarEditResponse(

        UUID grammarId,

        GrammarCreateRequest detail

) {
}