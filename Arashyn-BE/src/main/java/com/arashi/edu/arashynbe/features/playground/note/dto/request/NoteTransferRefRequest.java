package com.arashi.edu.arashynbe.features.playground.note.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record NoteTransferRefRequest(

        @NotNull
        UUID oldGrammarId,

        @NotNull
        UUID newGrammarId,

        @NotNull
        UUID creatorId

) {
}