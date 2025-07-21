package com.example.auth.service;

import java.util.concurrent.CompletableFuture;

/**
 * Defines the contract for an external One-Time Password service.
 */
public interface IOTPServiceAUTH {
    /**
     * Generates a secure OTP string.
     * @return A string representing the generated OTP.
     */
    String generate();

    /**
     * Sends an OTP to the specified phone number asynchronously.
     * @param phoneNumber The target phone number.
     * @param otp The OTP code to send.
     * @return A CompletableFuture indicating the success (true) or failure (false) of the operation.
     */
    CompletableFuture<Boolean> send(String phoneNumber, String otp);
}
```
```java
// src/main/java/com/example/auth/service/OTPServiceImplAUTH.java