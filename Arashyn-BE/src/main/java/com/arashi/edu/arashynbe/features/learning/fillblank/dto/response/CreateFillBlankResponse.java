package com.arashi.edu.arashynbe.features.learning.fillblank.dto.response;

import java.util.List;

public record CreateFillBlankResponse(
        List<Question> questions
) {

  public record Question(
          List<QuestionComponent> questionComponents,
          String meaning
  ) {

    public record QuestionComponent(

            String content,

            String answerToken,

            String iv,

            List<String> choices,

            int order

    ) {}
  }
}