package com.arashi.edu.arashynbe.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiError> handleApiException(
          ApiException ex,
          HttpServletRequest request
  ) {

    ErrorCode errorCode = ex.getErrorCode();

    ApiError error = ApiError.builder()
            .timestamp(Instant.now())
            .status(errorCode.getStatus().value())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(errorCode.getStatus())
            .body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(
          MethodArgumentNotValidException ex,
          HttpServletRequest request
  ) {

    Map<String, String> fieldErrors = new LinkedHashMap<>();

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    ApiError error = ApiError.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .code(ErrorCode.VALIDATION_FAILED.getCode())
            .message(ErrorCode.VALIDATION_FAILED.getMessage())
            .path(request.getRequestURI())
            .errors(fieldErrors)
            .build();

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(
          Exception ex,
          HttpServletRequest request
  ) {

    ApiError error = ApiError.builder()
            .timestamp(Instant.now())
            .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
            .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
            .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
            .body(error);
  }

}