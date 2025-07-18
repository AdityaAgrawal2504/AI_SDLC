package com.example.auth.service.impl;

import com.example.auth.exception.OtpServiceException;
import com.example.auth.service.IOtpService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;

/**
 * A mock implementation of the OTP service.
 * In a real application, this would integrate with an SMS gateway like Twilio or Vonage.
 */
@Service
@Log4j2
public class SmsOtpServiceImpl implements IOtpService {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Simulates generating and sending an OTP.
     * The OTP is logged to the console for development and testing purposes.
     *
     * @param phoneNumber The destination E.164 formatted phone number.
     * @return The generated 6-digit OTP string.
     * @throws OtpServiceException if the simulation logic decides to fail.
     */
    @Override
    public String generateAndSendOtp(String phoneNumber) {
        // Generate a 6-digit OTP
        String otp = new DecimalFormat("000000").format(random.nextInt(999999));

        // Simulate an API call to an SMS gateway
        log.info("--- MOCK OTP SERVICE ---");
        log.info("Sending OTP {} to phone number {}", otp, phoneNumber);
        log.info("--- END MOCK OTP SERVICE ---");

        // Simulate a potential failure of the external service
        if (phoneNumber.endsWith("000")) { // Test failure case
            throw new OtpServiceException("We could not send an OTP at this time. Please try again later.");
        }

        return otp;
    }
}
```
src/main/java/com/example/auth/service/IAuthenticationService.java
```java