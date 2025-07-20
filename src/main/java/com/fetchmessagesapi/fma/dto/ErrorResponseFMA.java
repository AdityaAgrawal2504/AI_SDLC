package com.fetchmessagesapi.fma.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * DTO for standardized error responses.
 */
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseFMA {
    private final String errorCode;
    private final String message;
    private Map<String, String> details;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/dto/MessageFMA.java