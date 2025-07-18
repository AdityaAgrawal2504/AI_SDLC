package com.example.userregistration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration for standardized, machine-readable error codes.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

    private final String code;
}
```
```java
// src/main/java/com/example/userregistration/dto/UserRegistrationRequest.java