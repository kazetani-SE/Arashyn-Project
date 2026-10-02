package com.arashi.edu.arashynbe.features.system.deck.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DeckUpdateRequest(

        @NotNull
        UUID id,

        @Size(max = 50)
        String name,

        @Size(max = 250)
        String description,

        Boolean isPublic,

        UUID oldFolderId,

        UUID newFolderId
) {
}