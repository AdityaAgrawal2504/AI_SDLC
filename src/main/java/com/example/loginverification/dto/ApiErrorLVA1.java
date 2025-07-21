package com.example.loginverification.dto;

import com.example.loginverification.enums.ErrorCodeLVA1;
import lombok.Getter;

/**
 * DTO for a standardized API error response.
 */
@Getter
public class ApiErrorLVA1 {
    private final String errorCode;
    private final String errorMessage;

    public ApiErrorLVA1(ErrorCodeLVA1 errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public ApiErrorLVA1(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
```
```java
// src/main/java/com/example/loginverification/exception/ApiExceptionLVA1.java