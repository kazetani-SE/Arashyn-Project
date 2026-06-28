package com.arashi.edu.arashynbe.shared.enums;

public enum Role {
  GUEST("guest"),
  USER("user"),
  CONTRIBUTOR("contributor"),
  ADMIN("admin");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Role fromValue(String value) {
    for (Role role : values()) {
      if (role.value.equals(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Unknown role: " + value);
  }
}