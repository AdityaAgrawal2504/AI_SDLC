package com.example.auth.service;

/**
 * Interface for a service that generates and sends One-Time Passwords (OTPs).
 */
public interface IOtpService {

    /**
     * Generates a new OTP and sends it to the specified phone number.
     *
     * @param phoneNumber The destination E.164 formatted phone number.
     * @return The generated OTP string.
     * @throws com.example.auth.exception.OtpServiceException if sending fails.
     */
    String generateAndSendOtp(String phoneNumber);
}
```
src/main/java/com/example/auth/service/impl/SmsOtpServiceImpl.java
```java