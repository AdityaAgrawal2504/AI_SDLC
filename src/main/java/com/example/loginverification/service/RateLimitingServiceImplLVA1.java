package com.example.loginverification.service;

import com.example.loginverification.enums.ErrorCodeLVA1;
import com.example.loginverification.exception.ApiExceptionLVA1;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mock implementation of the RateLimitingService.
 * In a production environment, this should be backed by a distributed cache like Redis or Hazelcast.
 */
@Service
public class RateLimitingServiceImplLVA1 implements RateLimitingServiceLVA1 {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION_MINUTES = 10;

    private final Map<String, AttemptInfo> attemptsCache = new ConcurrentHashMap<>();

    @Override
    public void checkRateLimit(String phoneNumber) {
        AttemptInfo attemptInfo = attemptsCache.get(phoneNumber);
        if (attemptInfo != null && attemptInfo.isLocked()) {
            throw new ApiExceptionLVA1(ErrorCodeLVA1.TOO_MANY_ATTEMPTS);
        }
    }

    @Override
    public void recordFailedAttempt(String phoneNumber) {
        AttemptInfo attemptInfo = attemptsCache.computeIfAbsent(phoneNumber, k -> new AttemptInfo());
        attemptInfo.increment();
    }

    @Override
    public void resetAttempts(String phoneNumber) {
        attemptsCache.remove(phoneNumber);
    }

    private static class AttemptInfo {
        private int count;
        private long lockoutEndTime;

        public AttemptInfo() {
            this.count = 0;
            this.lockoutEndTime = 0;
        }

        public synchronized void increment() {
            this.count++;
            if (this.count >= MAX_ATTEMPTS) {
                this.lockoutEndTime = Instant.now().plusSeconds(LOCKOUT_DURATION_MINUTES * 60).toEpochMilli();
            }
        }

        public synchronized boolean isLocked() {
            if (lockoutEndTime > 0) {
                if (Instant.now().toEpochMilli() < lockoutEndTime) {
                    return true; // Still locked
                } else {
                    // Lockout expired, reset
                    count = 0;
                    lockoutEndTime = 0;
                    return false;
                }
            }
            return false;
        }
    }
}
```
```java
// src/main/java/com/example/loginverification/service/OtpServiceLVA1.java