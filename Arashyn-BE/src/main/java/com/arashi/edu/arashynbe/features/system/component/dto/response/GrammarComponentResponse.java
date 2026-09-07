package com.arashi.edu.arashynbe.features.system.component.dto.response;

import java.util.UUID;

public record GrammarComponentResponse(

        UUID id,

        Integer order,

        String keyword,

        String form,

        Short groupKey,

        boolean optional

) {
}