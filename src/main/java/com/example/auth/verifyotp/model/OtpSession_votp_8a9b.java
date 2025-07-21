package com.example.auth.verifyotp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Represents the state of an OTP verification session. In a real application,
 * this would likely be a database entity (e.g., in Redis or a relational DB).
 */
@Data
@Builder
@AllArgsConstructor
public class OtpSession_votp_8a9b {
    /**
     * A unique identifier for the session, often a UUID.
     */
    private String sessionId;

    /**
     * The identifier for the user attempting to authenticate.
     */
    private String userId;

    /**
     * The securely stored OTP. In a real system, this should be a hash.
     */
    private String otp;

    /**
     * The timestamp when this session and its OTP will expire.
     */
    private Instant expiresAt;

    /**
     * The number of failed verification attempts made for this session.
     */
    private int attempts;

    /**
     * A flag indicating if the session has been successfully verified and used.
     */
    private boolean verified;

    /**
     * Increments the attempt counter for this session.
     */
    public void incrementAttempts() {
        this.attempts++;
    }

    /**
     * Checks if the session has expired based on the current time.
     * @return true if the session has expired, false otherwise.
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
```
```java