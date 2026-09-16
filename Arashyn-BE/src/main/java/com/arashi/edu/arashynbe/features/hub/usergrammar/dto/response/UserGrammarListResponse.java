package com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response;

import com.arashi.edu.arashynbe.features.system.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.system.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.system.meaning.dto.response.GrammarMeaningSummaryResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UserGrammarListResponse(

        List<UserGrammarSummarisedResponse> items,

        Long numberOfItems
) {

  public record UserGrammarSummarisedResponse(

          UUID id,

          String title,

          List<GrammarComponentSummaryResponse> components,

          List<GrammarMeaningSummaryResponse> meanings,

          List<GrammarFilterResponse> filters,

          Short proficiency,

          OffsetDateTime lastReviewAt

  ){}

}