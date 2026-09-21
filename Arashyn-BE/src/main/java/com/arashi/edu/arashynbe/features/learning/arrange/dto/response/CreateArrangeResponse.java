package com.arashi.edu.arashynbe.features.learning.arrange.dto.response;

import java.util.List;

public record CreateArrangeResponse(

        List<Question> questions,

        List<PoolItem> itemPool

) {

  public record Question(

          List<BlankComponent> components,

          String meaning

  ) {

    public record BlankComponent(

            String answerToken,

            String iv,

            int order

    ) {}
  }

  public record PoolItem(

          String id,

          String content

  ) {}
}