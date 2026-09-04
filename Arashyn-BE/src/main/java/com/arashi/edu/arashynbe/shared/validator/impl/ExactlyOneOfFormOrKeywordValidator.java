package com.arashi.edu.arashynbe.shared.validator.impl;

import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.shared.validator.ExactlyOneOfFormOrKeyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExactlyOneOfFormOrKeywordValidator
        implements ConstraintValidator<ExactlyOneOfFormOrKeyword, ComponentCreateRequest> {

  @Override
  public boolean isValid(
          ComponentCreateRequest component,
          ConstraintValidatorContext context
  ) {

    if (component == null) {
      return true;
    }

    boolean hasForm = component.formId() != null;
    boolean hasKeyword =
            component.keyword() != null &&
                    !component.keyword().isBlank();

    return hasForm ^ hasKeyword;
  }
}