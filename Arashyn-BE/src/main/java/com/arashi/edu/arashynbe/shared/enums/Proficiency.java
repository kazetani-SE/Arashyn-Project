package com.arashi.edu.arashynbe.shared.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Proficiency {

  NEW(0),
  RECOGNIZED(1),
  FAMILIAR(2),
  LEARNING(3),
  PROFICIENT(4),
  FLUENT(5),
  MASTERED(6);

  private final int value;

  Proficiency(int value) {
    this.value = value;
  }

  public static Proficiency fromValue(int value) {
    return Arrays.stream(values())
            .filter(p -> p.value == value)
            .findFirst()
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid proficiency: " + value));
  }

  public static int minValue() {
    return Arrays.stream(values())
            .mapToInt(Proficiency::getValue)
            .min()
            .orElseThrow();
  }

  public static int maxValue() {
    return Arrays.stream(values())
            .mapToInt(Proficiency::getValue)
            .max()
            .orElseThrow();
  }
}