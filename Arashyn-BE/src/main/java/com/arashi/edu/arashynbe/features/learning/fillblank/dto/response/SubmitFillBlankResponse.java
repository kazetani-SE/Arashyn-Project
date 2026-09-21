package com.arashi.edu.arashynbe.features.learning.fillblank.dto.response;

import java.util.List;

public record SubmitFillBlankResponse(

        List<AnswerResult> results,

        int correctCount,

        int totalCount

) {

  public record AnswerResult(
          String salt,
          boolean correct,
          String correctAnswer
  ) {}
}