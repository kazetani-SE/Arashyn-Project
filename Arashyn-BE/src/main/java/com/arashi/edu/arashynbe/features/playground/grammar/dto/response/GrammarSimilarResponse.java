package com.arashi.edu.arashynbe.features.playground.grammar.dto.response;

import com.arashi.edu.arashynbe.shared.enums.DuplicateType;

import java.util.List;
import java.util.UUID;

public record GrammarDuplicateResponse(

        List<GrammarDuplicateItem> fullMatches,

        List<GrammarDuplicateItem> partialMatches

) {

  public record GrammarDuplicateItem(

          UUID grammarId,

          DuplicateType type
          
  ) {
  }

}