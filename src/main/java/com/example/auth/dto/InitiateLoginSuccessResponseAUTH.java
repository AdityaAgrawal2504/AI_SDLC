package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the successful response after initiating a phone login.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitiateLoginSuccessResponseAUTH {
    private String status = "success";
    private String message;
    private String otpSessionId;
}
```
```java
// src/main/java/com/example/auth/dto/ErrorResponseAUTH.java