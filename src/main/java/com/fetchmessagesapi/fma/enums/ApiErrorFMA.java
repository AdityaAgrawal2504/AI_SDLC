package com.fetchmessagesapi.fma.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of standardized API error codes and messages.
 */
@Getter
@RequiredArgsConstructor
public enum ApiErrorFMA {
    AUTH_HEADER_MISSING(HttpStatus.UNAUTHORIZED, "E401_AUTH_HEADER_MISSING", "Authorization header is required."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E401_INVALID_TOKEN", "Authentication token is invalid, malformed, or expired."),
    INVALID_QUERY_PARAMETER(HttpStatus.BAD_REQUEST, "E400_INVALID_QUERY_PARAMETER", "A query parameter has an invalid value."),
    MESSAGE_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E500_MESSAGE_FETCH_FAILED", "An internal error occurred while processing the request."),
    UNHANDLED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E500_UNEXPECTED_ERROR", "An unexpected error occurred.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/enums/MessageStatusFMA.java