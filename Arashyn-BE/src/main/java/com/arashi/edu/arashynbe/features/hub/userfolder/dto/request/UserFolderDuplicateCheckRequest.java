package com.arashi.edu.arashynbe.features.hub.userfolder.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserFolderDuplicateCheckRequest(

        @NotNull
        UUID id

) {
}