package com.example.logininitiation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode_LIAPI_2001 {
    INVALID_INPUT("INVALID_INPUT"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED"),
    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED"),
    OTP_SERVICE_FAILURE("OTP_SERVICE_FAILURE"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    USER_NOT_FOUND("USER_NOT_FOUND");

    private final String code;
}
```
```java
// src/main/java/com/example/logininitiation/exception/ApiException_LIAPI_3001.java