package com.arashi.edu.arashynbe.features.system.note.dto.response;

import java.util.UUID;

public record GrammarNoteEditResponse(

        UUID id,

        String content,

        int groupKey,

        boolean isPublic

) {
}