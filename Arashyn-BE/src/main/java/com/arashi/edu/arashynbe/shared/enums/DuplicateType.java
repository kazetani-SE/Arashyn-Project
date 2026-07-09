package com.arashi.edu.arashynbe.shared.enums;

public enum DuplicateType {

  NONE("None"),
  PARTIAL("Partial"),
  FULL("Full");

  private final String label;

  DuplicateType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public boolean isDuplicate() {
    return this != NONE;
  }
}