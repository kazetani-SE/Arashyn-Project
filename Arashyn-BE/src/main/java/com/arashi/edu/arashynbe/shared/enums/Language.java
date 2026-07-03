package com.arashi.edu.arashynbe.shared.enums;

import lombok.Getter;

@Getter
public enum Language {
  VI("VI", "Vietnamese"),
  EN("EN", "English"),
  JA("JA", "Japanese"),
  KO("KO", "Korean"),
  ZH("ZH", "Chinese");

  private final String code;
  private final String displayName;

  Language(String code, String displayName) {
    this.code = code;
    this.displayName = displayName;
  }

  public static Language fromCode(String code) {
    for (Language lang : values()) {
      if (lang.getCode().equalsIgnoreCase(code)) {
        return lang;
      }
    }
    throw new IllegalArgumentException("Unknown language code: " + code);
  }
}