package com.arashi.edu.arashynbe.features.system.meaning.dto.response;

import com.arashi.edu.arashynbe.features.system.example.dto.response.GrammarExampleEditResponse;

import java.util.List;
import java.util.UUID;

public record GrammarMeaningEditResponse(

        UUID id,

        String content,

        Short groupKey,

        boolean isPublic,

        List<GrammarExampleEditResponse> examples

) {
}