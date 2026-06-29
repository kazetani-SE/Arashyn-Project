package com.arashi.edu.arashynbe.shared.validator.impl;

import com.arashi.edu.arashynbe.shared.validator.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

  private static final int MIN_LENGTH = 8;
  private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null || password.length() < MIN_LENGTH) {
      return false;
    }

    boolean hasUpper = false;
    boolean hasLower = false;
    boolean hasNumber = false;
    boolean hasSpecial = false;

    for (char ch : password.toCharArray()) {
      if (Character.isUpperCase(ch)) {
        hasUpper = true;
      } else if (Character.isLowerCase(ch)) {
        hasLower = true;
      } else if (Character.isDigit(ch)) {
        hasNumber = true;
      } else if (SPECIAL_CHARS.indexOf(ch) >= 0) {
        hasSpecial = true;
      }
    }

    return hasUpper && hasLower && hasNumber && hasSpecial;
  }
}