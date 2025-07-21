package com.example.fetchconversationsapi1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * DTO for representing API errors in a structured format.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto_FCAPI_1 {
    private final String errorCode;
    private final String message;
    private Map<String, String> details;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/dto/MessageSnippetDto_FCAPI_1.java