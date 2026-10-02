package com.arashi.edu.arashynbe.features.system.deck.dto.response;

import java.util.Set;
import java.util.UUID;

public record DeckCheckUpdateResponse(

        boolean hasUpdate,

        Set<UUID> addedGrammarIds,

        Set<UUID> removedGrammarIds

) {}