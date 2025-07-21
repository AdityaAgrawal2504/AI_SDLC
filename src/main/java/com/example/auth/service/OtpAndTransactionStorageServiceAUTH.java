package com.example.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Manages the temporary storage of OTPs and their associated transaction data.
 * Uses Spring's Cache abstraction (backed by Caffeine in this configuration).
 */
@Service
public class OtpAndTransactionStorageServiceAUTH {

    private final Cache otpCache;

    /**
     * Initializes the service by getting the 'otp-storage' cache from the CacheManager.
     * @param cacheManager The configured Spring CacheManager.
     */
    @Autowired
    public OtpAndTransactionStorageServiceAUTH(CacheManager cacheManager) {
        this.otpCache = cacheManager.getCache("otp-storage");
    }

    /**
     * Stores the OTP and phone number against a unique transaction ID.
     * @param transactionId The unique identifier for the login attempt.
     * @param phoneNumber The user's phone number.
     * @param otp The generated OTP.
     */
    public void storeOtp(String transactionId, String phoneNumber, String otp) {
        if (otpCache != null) {
            otpCache.put(transactionId, new OtpData(phoneNumber, otp));
        }
    }

    /**
     * Simple record to hold the data associated with an OTP transaction.
     */
    public record OtpData(String phoneNumber, String otp) {}
}
```
```java
// src/main/java/com/example/auth/service/IAuthServiceAUTH.java