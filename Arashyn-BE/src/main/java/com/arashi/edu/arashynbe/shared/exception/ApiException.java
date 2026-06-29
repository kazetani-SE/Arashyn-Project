package com.arashi.edu.arashynbe.shared.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

  private final ErrorCode errorCode;

  public ApiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}