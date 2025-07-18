package com.example.userregistration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.userregistration.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for standardized API error responses.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private ErrorCode errorCode;
    private String message;
    private List<ErrorDetail> details;
}
```
```java
// src/main/java/com/example/userregistration/dto/ErrorDetail.java