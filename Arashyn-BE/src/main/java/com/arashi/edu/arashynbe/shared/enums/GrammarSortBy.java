package com.arashi.edu.arashynbe.shared.enums;

public enum GrammarSortBy {

  CREATED_AT("createdAt"),
  UPDATED_AT("updatedAt"),
  TITLE("title");

  private final String field;

  GrammarSortBy(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}