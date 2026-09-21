package com.arashi.edu.arashynbe.features.learning.arrange.dto.response;

import java.util.List;

public record SubmitArrangeResponse(

        List<AnswerResult> results,

        int correctCount,

        int totalCount

) {

  public record AnswerResult(
          String iv,
          boolean correct,
          String correctAnswer
  ) {}
}