package com.example.auth.service;

import com.example.auth.entity.UserAUTH;
import com.example.auth.exception.AuthServiceExceptionAUTH;
import com.example.auth.util.ErrorCodeAUTH;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;

/**
 * Mock implementation of the OTP service. In a real app, this would integrate with an SMS provider.
 */
@Service
@Log4j2
public class OtpServiceImplAUTH implements OtpServiceAUTH {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Simulates generating and sending an OTP. It logs the OTP instead of sending an SMS.
     */
    @Override
    public String generateAndSendOtp(UserAUTH user) {
        try {
            // Generate a 6-digit OTP
            String otp = new DecimalFormat("000000").format(random.nextInt(999999));

            // Simulate sending the OTP via a third-party service
            log.info("Simulating OTP dispatch for user [{}]. OTP: [{}]. Phone: [{}].",
                    user.getId(), otp, user.getPhoneNumber());

            // In a real application, if the SMS provider call failed, you would throw this:
            // throw new AuthServiceExceptionAUTH(ErrorCodeAUTH.OTP_SERVICE_UNAVAILABLE);

            return otp;
        } catch (Exception e) {
            log.error("Failed to generate or send OTP for user {}", user.getId(), e);
            throw new AuthServiceExceptionAUTH(ErrorCodeAUTH.OTP_SERVICE_UNAVAILABLE, e);
        }
    }
}
```
```java
// src/main/java/com/example/auth/service/AuthServiceAUTH.java