package com.arashi.edu.arashynbe.features.playground.meaning.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MeaningTransferRefRequest(

        @NotNull
        UUID oldGrammarId,

        @NotNull
        UUID newGrammarId,

        @NotNull
        UUID creatorId

) {
}