package com.arashi.edu.arashynbe.features.playground.component.dto.response;

import java.util.UUID;

public record GrammarComponentEditResponse(

        UUID id,

        Integer order,

        String keyword,

        UUID formId,

        Short groupKey,

        boolean optional

) {
}