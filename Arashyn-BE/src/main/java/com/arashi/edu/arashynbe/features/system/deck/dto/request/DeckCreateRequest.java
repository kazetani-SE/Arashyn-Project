package com.arashi.edu.arashynbe.features.system.deck.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record DeckCreateRequest(

        @NotBlank
        @Size(max = 50)
        String name,

        @Size(max = 250)
        String description,

        @NotNull
        Language language,

        Boolean isPublic,

        UUID folderId,

        Set<UUID> grammarIds
) {
}