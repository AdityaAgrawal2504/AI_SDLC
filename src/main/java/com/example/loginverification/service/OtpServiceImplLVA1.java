package com.example.loginverification.service;

import com.example.loginverification.enums.ErrorCodeLVA1;
import com.example.loginverification.exception.ApiExceptionLVA1;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mock implementation of the OtpService.
 * This simulates storing and retrieving OTPs. In a real application, this would
 * interact with a secure database or a distributed cache like Redis.
 */
@Service
public class OtpServiceImplLVA1 implements OtpServiceLVA1 {

    private static final long OTP_VALIDITY_MINUTES = 5;

    // Mock storage for active OTPs. Key: phoneNumber, Value: OtpData
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();

    public OtpServiceImplLVA1() {
        // Pre-populate with a valid user and OTP for demonstration purposes.
        // This simulates a user having requested an OTP previously.
        String samplePhoneNumber = "+14155552671";
        String sampleOtp = "123456";
        long creationTime = Instant.now().toEpochMilli();
        otpStorage.put(samplePhoneNumber, new OtpData(sampleOtp, creationTime));

        String expiredOtpUser = "+14155552672";
        String expiredOtp = "654321";
        long expiredCreationTime = Instant.now().minusSeconds((OTP_VALIDITY_MINUTES + 1) * 60).toEpochMilli();
        otpStorage.put(expiredOtpUser, new OtpData(expiredOtp, expiredCreationTime));
    }

    @Override
    public void verifyOtp(String phoneNumber, String otp) {
        OtpData storedOtpData = otpStorage.get(phoneNumber);

        if (storedOtpData == null) {
            throw new ApiExceptionLVA1(ErrorCodeLVA1.USER_NOT_FOUND);
        }

        if (isOtpExpired(storedOtpData.getCreationTime())) {
            otpStorage.remove(phoneNumber); // Clean up expired OTP
            throw new ApiExceptionLVA1(ErrorCodeLVA1.OTP_EXPIRED);
        }

        if (!storedOtpData.getOtp().equals(otp)) {
            throw new ApiExceptionLVA1(ErrorCodeLVA1.INVALID_OTP);
        }

        // OTP is valid and used, remove it to prevent reuse
        otpStorage.remove(phoneNumber);
    }

    private boolean isOtpExpired(long creationTime) {
        long now = Instant.now().toEpochMilli();
        long diff = now - creationTime;
        return diff > (OTP_VALIDITY_MINUTES * 60 * 1000);
    }

    // Inner class to hold OTP data
    private record OtpData(String otp, long creationTime) {
        public String getOtp() {
            return otp;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}
```
```java
// src/main/java/com/example/loginverification/service/TokenServiceLVA1.java