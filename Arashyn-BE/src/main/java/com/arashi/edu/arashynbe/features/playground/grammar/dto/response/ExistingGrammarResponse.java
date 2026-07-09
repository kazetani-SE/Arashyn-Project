package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import java.util.Optional;
import java.util.UUID;

public record ExistingGrammarResponse(
        Optional<UUID> grammarId
) {
}