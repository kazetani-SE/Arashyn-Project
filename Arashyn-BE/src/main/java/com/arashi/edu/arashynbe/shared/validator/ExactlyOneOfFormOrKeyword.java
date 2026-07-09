package com.arashi.edu.arashynbe.shared.validator;

import com.arashi.edu.arashynbe.shared.validator.impl.ExactlyOneOfFormOrKeywordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExactlyOneOfFormOrKeywordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExactlyOneOfFormOrKeyword {

  String message() default "Exactly one of formId or keyWord must be provided.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}