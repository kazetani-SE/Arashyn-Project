package com.arashi.edu.arashynbe.features.learning.fillblank.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SubmitFillBlankRequest(

        @NotEmpty
        List<SubmitAnswer> answers

) {

  public record SubmitAnswer(

          @NotNull
          String iv,

          @NotNull
          String answerToken,

          String userAnswer

  ) {}
}