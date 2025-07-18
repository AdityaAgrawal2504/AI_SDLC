package com.example.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.auth.exception.ErrorCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Standardized error response structure.
 */
@Data
public class ApiErrorDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;

    public ApiErrorDTO(int status, ErrorCode errorCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
```
src/main/java/com/example/auth/model/UserStatus.java
```java