package com.arashi.edu.arashynbe.features.system.example.dto.response;

import java.util.UUID;

public record GrammarExampleResponse(

        UUID id,

        String sentence,

        String translation,

        String note

) {
}