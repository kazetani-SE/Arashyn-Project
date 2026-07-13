package com.arashi.edu.arashynbe.features.playground.example.dto.response;

import java.util.UUID;

public record GrammarExampleEditResponse(

        UUID id,

        String sentence,

        String translation,

        String note,

        boolean isPublic

) {
}