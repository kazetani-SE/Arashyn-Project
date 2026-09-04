package com.arashi.edu.arashynbe.features.playground.filter.dto.response;

import java.util.List;
import java.util.UUID;

public record ListSystemFilterResponse(
        List<SystemFilterResponse> systemFilters
) {

  public record SystemFilterResponse(

          UUID id,

          String name

  ) {
  }

}