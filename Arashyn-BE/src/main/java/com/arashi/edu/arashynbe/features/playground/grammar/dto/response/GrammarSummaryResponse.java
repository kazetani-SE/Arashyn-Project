package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningSummaryResponse;

import java.util.List;
import java.util.UUID;

public record GrammarSummaryResponse(

        UUID id,

        String title,

        List<GrammarComponentSummaryResponse> components,

        List<GrammarMeaningSummaryResponse> meanings,

        List<GrammarFilterResponse> filters

){
}