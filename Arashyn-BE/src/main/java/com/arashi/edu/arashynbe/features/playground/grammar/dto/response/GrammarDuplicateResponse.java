package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import com.arashi.edu.arashynbe.shared.enums.DuplicateType;

import java.util.List;
import java.util.UUID;

public record GrammarDuplicateResponse(
        DuplicateType type,
        List<UUID> grammarIds
) {
}