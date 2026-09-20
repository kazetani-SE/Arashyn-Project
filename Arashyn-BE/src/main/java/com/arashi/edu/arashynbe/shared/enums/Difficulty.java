package com.arashi.edu.arashynbe.shared.enums;

public enum Difficulty {

  EASY("Easy"),
  MEDIUM("Medium"),
  HARD("Hard");

  private final String label;

  Difficulty(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}