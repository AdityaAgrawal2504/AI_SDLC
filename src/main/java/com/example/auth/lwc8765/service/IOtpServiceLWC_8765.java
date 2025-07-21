package com.example.auth.lwc8765.service;

import com.example.auth.lwc8765.exception.ServiceUnavailableExceptionLWC_8765;

public interface IOtpServiceLWC_8765 {
    /**
     * Generates and sends an OTP to the user's phone number.
     * @param phoneNumber The target phone number.
     * @throws ServiceUnavailableExceptionLWC_8765 if the OTP service fails.
     */
    void sendOtp(String phoneNumber) throws ServiceUnavailableExceptionLWC_8765;
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/OtpServiceLWC_8765.java