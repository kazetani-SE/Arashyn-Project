package com.arashi.edu.arashynbe.features.hub.userdeck.dto.request;

import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateMultipleRequest;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDeckCloneRequest(

        @NotNull
        UUID deckId,

        String name,

        UUID userFolderId,

        UUID sourceUserDeckId,

        UserGrammarCreateMultipleRequest userGrammars

) {}