package com.arashi.edu.arashynbe.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // ---------- Validation ----------
  INVALID_JSON(
          HttpStatus.BAD_REQUEST,
          "INVALID_JSON",
          "Malformed JSON request."
  ),

  VALIDATION_FAILED(
          HttpStatus.BAD_REQUEST,
          "VALIDATION_FAILED",
          "Request validation failed."
  ),

  // ---------- Authentication ----------
  INVALID_CREDENTIALS(
          HttpStatus.UNAUTHORIZED,
          "INVALID_CREDENTIALS",
          "Invalid email or password."
  ),

  EMAIL_ALREADY_EXISTS(
          HttpStatus.CONFLICT,
          "EMAIL_ALREADY_EXISTS",
          "Email already exists."
  ),

  USERNAME_ALREADY_EXISTS(
          HttpStatus.CONFLICT,
          "USERNAME_ALREADY_EXISTS",
          "Username already exists."
  ),

  // ---------- Authorization ----------
  FORBIDDEN(
          HttpStatus.FORBIDDEN,
          "FORBIDDEN",
          "You do not have permission."
  ),

  // ---------- Resource ----------
  USER_NOT_FOUND(
          HttpStatus.NOT_FOUND,
          "USER_NOT_FOUND",
          "User not found."
  ),

  // ---------- Server ----------
  INTERNAL_SERVER_ERROR(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "INTERNAL_SERVER_ERROR",
          "Unexpected server error."
  ),

  // ---------- Grammar ----------
  GRAMMAR_CREATE_FAILED(
          HttpStatus.BAD_REQUEST,
          "GRAMMAR_CREATE_FAILED",
          "Failed to create grammar."
  ),

  // --------- Meaning ------------
  GRAMMAR_NOT_FOUND(
          HttpStatus.NOT_FOUND,
          "GRAMMAR_NOT_FOUND",
          "Grammar not found."
  ),

  MEANING_NOT_FOUND(
          HttpStatus.NOT_FOUND,
          "MEANING_NOT_FOUND",
          "Meaning not found."
  ),

  INVALID_GROUP_KEY(
          HttpStatus.BAD_REQUEST,
          "INVALID_GROUP_KEY",
          "Invalid component group."
  ),

  // --------- Component ---------
  FORM_ALREADY_EXISTS(
          HttpStatus.CONFLICT,
        "FORM_ALREADY_EXISTS",
                "Form already exists."
  ),

  FORM_NOT_FOUND(
          HttpStatus.NOT_FOUND,
        "FORM_NOT_FOUND",
                "Form not found."
  ),

  INVALID_COMPONENT_REFERENCE(
          HttpStatus.BAD_REQUEST,
        "INVALID_COMPONENT_REFERENCE",
                "Exactly one of keyword or formId must be provided."
  );

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}