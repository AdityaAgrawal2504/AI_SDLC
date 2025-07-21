package com.example.auth.lwc8765.service;

public interface IJwtServiceLWC_8765 {
    /**
     * Generates a short-lived JWT for the OTP verification step.
     * @param phoneNumber The user's phone number to be encoded in the token.
     * @return A JWT string.
     */
    String generateTempSessionToken(String phoneNumber);
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/JwtServiceLWC_8765.java