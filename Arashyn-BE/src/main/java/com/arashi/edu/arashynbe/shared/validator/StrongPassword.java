package com.arashi.edu.arashynbe.shared.validator;

import com.arashi.edu.arashynbe.shared.validator.impl.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
  String message() default "Password must contain uppercase, lowercase, numbers, and special characters!";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}