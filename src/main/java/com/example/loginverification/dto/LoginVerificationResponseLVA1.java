package com.example.loginverification.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for a successful login verification response.
 */
@Data
@Builder
public class LoginVerificationResponseLVA1 {
    private String authToken;
    private String tokenType;
    private long expiresIn;
}
```
```java
// src/main/java/com/example/loginverification/dto/ApiErrorLVA1.java