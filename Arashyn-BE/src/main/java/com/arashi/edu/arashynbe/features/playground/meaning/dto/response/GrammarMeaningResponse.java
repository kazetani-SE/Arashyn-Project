package com.arashi.edu.arashynbe.features.playground.meaning.dto.response;

import com.arashi.edu.arashynbe.features.playground.example.dto.response.GrammarExampleResponse;

import java.util.List;
import java.util.UUID;

public record GrammarMeaningResponse(

        UUID id,

        String content,

        Short groupKey,

        List<GrammarExampleResponse> examples

) {
}