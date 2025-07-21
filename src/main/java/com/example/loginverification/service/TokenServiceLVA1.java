package com.example.loginverification.service;

import com.example.loginverification.dto.LoginVerificationResponseLVA1;

/**
 * Interface for generating authentication tokens.
 */
public interface TokenServiceLVA1 {

    /**
     * Generates a new session authentication token for the given subject (e.g., phone number).
     * @param subject The identifier for whom the token is generated.
     * @return A LoginVerificationResponse DTO containing the token details.
     */
    LoginVerificationResponseLVA1 generateToken(String subject);
}
```
```java
// src/main/java/com/example/loginverification/service/TokenServiceImplLVA1.java