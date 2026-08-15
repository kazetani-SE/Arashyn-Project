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

  INVALID_ACCESS_TOKEN(
          HttpStatus.UNAUTHORIZED,
          "INVALID_ACCESS_TOKEN",
          "Access token is invalid or malformed."
  ),

  INVALID_REFRESH_TOKEN(
          HttpStatus.UNAUTHORIZED,
          "INVALID_REFRESH_TOKEN",
          "Refresh token is invalid or expired."
  ),

  SESSION_NOT_FOUND(
          HttpStatus.UNAUTHORIZED,
          "SESSION_NOT_FOUND",
          "No active session found for this account."
  ),

  // ---------- Registration ----------
  EMAIL_NOT_VERIFIED(
          HttpStatus.BAD_REQUEST,
          "EMAIL_NOT_VERIFIED",
          "Email verification has not been completed."
  ),

  REGISTRATION_PAYLOAD_INVALID(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "REGISTRATION_PAYLOAD_INVALID",
          "Stored registration data is invalid."
  ),

  // ---------- Authorization ----------
  FORBIDDEN(
          HttpStatus.FORBIDDEN,
          "FORBIDDEN",
          "You do not have permission."
  ),

  // ---------- Registration OTP ----------
  PENDING_REGISTRATION_NOT_FOUND(
          HttpStatus.NOT_FOUND,
          "PENDING_REGISTRATION_NOT_FOUND",
          "No pending registration found for this email."
  ),

  VERIFICATION_CODE_INVALID(
          HttpStatus.BAD_REQUEST,
          "VERIFICATION_CODE_INVALID",
          "Invalid verification code."
  ),

  VERIFICATION_CODE_EXPIRED(
          HttpStatus.BAD_REQUEST,
          "VERIFICATION_CODE_EXPIRED",
          "Verification code has expired."
  ),

  VERIFICATION_CODE_RESEND_COOLDOWN(
          HttpStatus.TOO_MANY_REQUESTS,
          "VERIFICATION_CODE_RESEND_COOLDOWN",
          "Please wait before requesting another verification code."
  ),

  REGISTRATION_PAYLOAD_SERIALIZATION_FAILED(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "REGISTRATION_PAYLOAD_SERIALIZATION_FAILED",
          "Failed to store registration data."
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

  // ---------- Email ----------
  EMAIL_TEMPLATE_PROCESSING_FAILED(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "EMAIL_TEMPLATE_PROCESSING_FAILED",
          "Failed to create email from templates."
  ),

  VERIFICATION_EMAIL_SEND_FAILED(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "VERIFICATION_EMAIL_SEND_FAILED",
          "Failed to send verification email."
  ),

  // ---------- Grammar ----------
  GRAMMAR_CREATE_FAILED(
          HttpStatus.BAD_REQUEST,
          "GRAMMAR_CREATE_FAILED",
          "Failed to create grammar."
  ),

  GRAMMAR_ALREADY_EXISTS(
          HttpStatus.CONFLICT,
          "GRAMMAR_ALREADY_EXISTS",
          "Grammar already exists."
  ),

  GRAMMAR_DELETE_FAILED(
          HttpStatus.BAD_REQUEST,
          "GRAMMAR_DELETE_FAILED",
          "Failed to delete grammar."
  ),

  GRAMMAR_EXTEND_FAILED(
          HttpStatus.BAD_REQUEST,
          "GRAMMAR_EXTEND_FAILED",
          "Failed to extend grammar."
  ),

  EMPTY_EXTEND_REQUEST(
          HttpStatus.BAD_REQUEST,
          "EMPTY_EXTEND_REQUEST",
          "At least one meaning, note, or filter must be provided."
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

  // --------- Filter -----------
  FILTER_ALREADY_EXISTS(
          HttpStatus.CONFLICT,
          "FILTER_ALREADY_EXISTS",
          "Filter already exists."
  ),

  FILTER_NOT_FOUND(
          HttpStatus.NOT_FOUND,
          "FILTER_NOT_FOUND",
          "Filter not found."
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