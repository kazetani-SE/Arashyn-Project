package com.arashi.edu.arashynbe.features.hub.userfolder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserFolderCreateRequest(

        @NotBlank
        String name,

        @NotNull
        Boolean isPublic,

        UUID userFolderId

) {
}