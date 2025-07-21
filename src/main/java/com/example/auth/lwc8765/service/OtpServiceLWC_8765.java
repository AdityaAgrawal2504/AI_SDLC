package com.example.auth.lwc8765.service;

import com.example.auth.lwc8765.exception.ServiceUnavailableExceptionLWC_8765;
import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpServiceLWC_8765 implements IOtpServiceLWC_8765 {

    private static final Logger log = LogManager.getLogger(OtpServiceLWC_8765.class);

    /**
     * Simulates generating and sending an OTP.
     */
    @Override
    public void sendOtp(String phoneNumber) throws ServiceUnavailableExceptionLWC_8765 {
        long startTime = System.currentTimeMillis();

        // Simulate failure for a specific number to test error handling
        if ("9999999999".equals(phoneNumber)) {
            throw new ServiceUnavailableExceptionLWC_8765(
                    ErrorCodeLWC_8765.OTP_SERVICE_FAILURE,
                    "OTP service provider is currently unavailable."
            );
        }

        String otp = String.format("%06d", ThreadLocalRandom.current().nextInt(100000, 999999));

        // In a real application, this would call an external SMS gateway API.
        // We are just logging it for this implementation.
        log.info("Simulating OTP dispatch. Phone: [{}], OTP: [{}]", phoneNumber, otp);

        long endTime = System.currentTimeMillis();
        ThreadContext.put("executionTime", String.valueOf(endTime - startTime));
        log.info("Successfully dispatched OTP for phone number: {}", phoneNumber);
        ThreadContext.clearMap();
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/IAuthenticationServiceLWC_8765.java