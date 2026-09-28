package com.arashi.edu.arashynbe.features.system.folder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FolderCreateRequest(

        @NotBlank
        String name,

        @NotNull
        Boolean isPublic,

        UUID parentId

) {
}