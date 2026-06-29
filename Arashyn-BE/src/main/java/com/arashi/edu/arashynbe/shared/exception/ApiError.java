package com.arashi.edu.arashynbe.shared.exception;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
public record ApiError(
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        Map<String, String> errors
) {
}