package com.example.loginverification.service;

/**
 * Interface for managing and verifying One-Time Passwords (OTPs).
 */
public interface OtpServiceLVA1 {

    /**
     * Verifies the provided OTP against the stored one for the given phone number.
     * @param phoneNumber The user's phone number.
     * @param otp The OTP to verify.
     * @throws com.example.loginverification.exception.ApiExceptionLVA1 if the user is not found, OTP is invalid, or OTP has expired.
     */
    void verifyOtp(String phoneNumber, String otp);
}
```
```java
// src/main/java/com/example/loginverification/service/OtpServiceImplLVA1.java