package com.arashi.edu.arashynbe.features.system.deck.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record DeckAssignGrammarRequest(

        @NotNull
        UUID deckId,

        @NotNull
        Set<UUID> grammarIds

) {
}