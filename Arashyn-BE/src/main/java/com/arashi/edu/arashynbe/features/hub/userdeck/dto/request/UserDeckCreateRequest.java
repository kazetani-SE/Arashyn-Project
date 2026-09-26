package com.arashi.edu.arashynbe.features.hub.userdeck.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserDeckCreateRequest(

        @NotBlank
        String name,

        @Size(max = 250)
        String description,

        @NotNull
        Language language,

        @NotNull
        Boolean isPublic,

        UUID userFolderId

) {
}