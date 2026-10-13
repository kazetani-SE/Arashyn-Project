package com.arashi.edu.arashynbe.features.hub.userdeck.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UserDeckCloneRequest(

        @NotNull
        UUID deckId,

        String name,

        UUID userFolderId,

        UUID sourceUserDeckId,

        List<UserDeckCloneGrammarRequest> userGrammars

) {

        public record UserDeckCloneGrammarRequest(

                UUID grammarId,

                UUID sourceUserGrammarId

        ){}

}