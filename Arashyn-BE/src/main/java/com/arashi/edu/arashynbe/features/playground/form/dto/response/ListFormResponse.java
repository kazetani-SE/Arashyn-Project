package com.arashi.edu.arashynbe.features.playground.form.dto.response;

import java.util.List;
import java.util.UUID;

public record ListFormResponse(
        List<FormResponse> forms
) {

  public record FormResponse(
          UUID id,

          String name,

          String type
  ) {}

}