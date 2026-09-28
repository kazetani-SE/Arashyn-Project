package com.arashi.edu.arashynbe.features.system.folder.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FolderUpdateRequest(

        @NotNull
        UUID id,

        String name,

        Boolean isPublic,

        UUID oldParentId,

        UUID newParentId
) {
}