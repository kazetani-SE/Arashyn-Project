package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import com.arashi.edu.arashynbe.shared.enums.SimilarType;

import java.util.List;
import java.util.UUID;

public record GrammarSimilarResponse(

        List<GrammarSimilarItem> matches

) {

  public record GrammarSimilarItem(

          UUID grammarId,

          SimilarType type

  ) {
  }

}