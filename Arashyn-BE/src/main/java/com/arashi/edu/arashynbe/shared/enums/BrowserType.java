package com.arashi.edu.arashynbe.shared.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BrowserType {
  GRAMMAR("grammar"),
  DECK("deck"),
  FOLDER("folder");

  private final String value;

  BrowserType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static BrowserType fromValue(String value) {
    return Arrays.stream(values())
            .filter(type -> type.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Unknown type: " + value
                    )
            );
  }
}