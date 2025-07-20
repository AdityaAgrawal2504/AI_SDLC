package com.example.auth.service;

import com.example.auth.entity.UserAUTH;

/**
 * Interface for an OTP (One-Time Password) service.
 */
public interface OtpServiceAUTH {
    /**
     * Generates an OTP and sends it to the user's phone number.
     */
    String generateAndSendOtp(UserAUTH user);
}
```
```java
// src/main/java/com/example/auth/service/OtpServiceImplAUTH.java