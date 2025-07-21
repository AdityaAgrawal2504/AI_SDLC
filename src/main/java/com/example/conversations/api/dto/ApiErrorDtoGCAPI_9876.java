package com.example.conversations.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * DTO for standardized API error responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDtoGCAPI_9876 {
    private String errorCode;
    private String message;
    private Map<String, String> details;
}
```
```java
// src/main/java/com/example/conversations/api/dto/ConversationDtoGCAPI_9876.java