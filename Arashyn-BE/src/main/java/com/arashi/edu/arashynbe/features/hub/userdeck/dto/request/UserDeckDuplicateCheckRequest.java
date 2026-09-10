package com.arashi.edu.arashynbe.features.hub.userdeck.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDeckDuplicateCheckRequest(

        @NotNull
        UUID deckId

) {
}