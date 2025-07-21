package com.example.loginverification.exception;

import com.example.loginverification.enums.ErrorCodeLVA1;
import lombok.Getter;

/**
 * Custom runtime exception to represent specific API errors.
 */
@Getter
public class ApiExceptionLVA1 extends RuntimeException {

    private final ErrorCodeLVA1 errorCode;

    public ApiExceptionLVA1(ErrorCodeLVA1 errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/loginverification/logging/StructuredLoggerLVA1.java