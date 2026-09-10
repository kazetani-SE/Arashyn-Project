package com.arashi.edu.arashynbe.features.hub.userdeck.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDeckUpdateRequest(

        @NotNull
        UUID userDeckId,

        String name,

        UUID currentUserFolderId,

        UUID newUserFolderId
) {
}