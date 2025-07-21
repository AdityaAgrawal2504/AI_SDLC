package com.example.loginverification.service;

/**
 * Interface for rate limiting logic to prevent brute-force attacks.
 */
public interface RateLimitingServiceLVA1 {

    /**
     * Checks if a verification attempt is allowed for a given phone number.
     * @param phoneNumber The phone number attempting verification.
     * @throws com.example.loginverification.exception.ApiExceptionLVA1 if the rate limit is exceeded.
     */
    void checkRateLimit(String phoneNumber);

    /**
     * Records a failed verification attempt for the given phone number.
     * @param phoneNumber The phone number with the failed attempt.
     */
    void recordFailedAttempt(String phoneNumber);

    /**
     * Resets the attempt counter for a given phone number upon successful verification.
     * @param phoneNumber The phone number to reset.
     */
    void resetAttempts(String phoneNumber);
}
```
```java
// src/main/java/com/example/loginverification/service/RateLimitingServiceImplLVA1.java