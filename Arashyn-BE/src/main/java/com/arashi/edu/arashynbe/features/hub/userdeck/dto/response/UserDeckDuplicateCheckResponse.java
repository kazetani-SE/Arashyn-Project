package com.arashi.edu.arashynbe.features.hub.userdeck.dto.response;

import java.util.UUID;

public record UserDeckDuplicateCheckResponse(

        boolean duplicated,

        UUID existingUserDeckId

) {
}