package com.arashi.edu.arashynbe.shared.enums;

public enum SimilarType {

  NONE("None"),
  PARTIAL("Partial"),
  FULL("Full");

  private final String label;

  SimilarType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public boolean isDuplicate() {
    return this != NONE;
  }
}