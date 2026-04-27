package com.githubx.githubfilesms.util.errorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        List<FieldError> fieldErrors
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, Instant.now(), null);
    }

    public ErrorResponse(int status, String error, String message, String path, List<FieldError> fieldErrors) {
        this(status, error, message, path, Instant.now(), fieldErrors);
    }

    public record FieldError(String field, String message) {}
}
