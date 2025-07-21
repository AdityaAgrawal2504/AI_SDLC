package com.example.logininitiation.service;

/**
 * Responsible for generating, storing, and sending OTPs.
 */
public interface IOTPService_LIAPI_6002 {

    /**
     * Generates a new OTP, associates it with the correlation ID, and sends it via an SMS provider.
     * @param phoneNumber The recipient's phone number.
     * @param correlationId A unique ID to link this OTP to a specific login attempt.
     */
    void generateAndSendOtp(String phoneNumber, String correlationId);
}
```
```java
// src/main/java/com/example/logininitiation/service/IUserService_LIAPI_6001.java