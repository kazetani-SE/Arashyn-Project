package com.arashi.edu.arashynbe.features.playground.note.dto.response;

import java.util.UUID;

public record GrammarNoteEditResponse(

        UUID id,

        String content,

        int groupKey,

        boolean isPublic

) {
}