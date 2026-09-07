package com.arashi.edu.arashynbe.features.system.deck.dto.response;

import com.arashi.edu.arashynbe.shared.enums.Language;

import java.util.List;
import java.util.UUID;

public record DeckListResponse(

        List<DeckSummariseResponse> deckList

) {

  public record DeckSummariseResponse(

          UUID id,

          String name,

          String description,

          Language language,

          UUID ownerId

  ){}

}