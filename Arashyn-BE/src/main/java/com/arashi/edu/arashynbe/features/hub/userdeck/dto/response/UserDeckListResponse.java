package com.arashi.edu.arashynbe.features.hub.userdeck.dto.response;

import com.arashi.edu.arashynbe.shared.enums.Language;

import java.util.List;
import java.util.UUID;

public record UserDeckListResponse(

        List<UserDeckSummariseResponse> userDecks

) {

  public record UserDeckSummariseResponse(

          UUID id,

          String name,

          String description,

          Language language

  ){}

}