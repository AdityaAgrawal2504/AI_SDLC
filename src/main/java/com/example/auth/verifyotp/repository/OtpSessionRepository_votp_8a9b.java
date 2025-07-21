package com.example.auth.verifyotp.repository;

import com.example.auth.verifyotp.model.OtpSession_votp_8a9b;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An in-memory repository for OTP sessions.
 * This serves as a mock for a persistent store like Redis or a database.
 * NOTE: This is for demonstration purposes and is not suitable for production.
 */
@Repository
public class OtpSessionRepository_votp_8a9b {

    private final Map<String, OtpSession_votp_8a9b> sessionStore = new ConcurrentHashMap<>();

    /**
     * Finds an OTP session by its unique session ID.
     * @param sessionId The ID of the session to retrieve.
     * @return An Optional containing the session if found, otherwise empty.
     */
    public Optional<OtpSession_votp_8a9b> findById(String sessionId) {
        // In a real application, you would also clean up expired sessions here or in a background job.
        return Optional.ofNullable(sessionStore.get(sessionId));
    }

    /**
     * Saves or updates an OTP session in the store.
     * @param session The session object to save.
     * @return The saved session object.
     */
    public OtpSession_votp_8a9b save(OtpSession_votp_8a9b session) {
        sessionStore.put(session.getSessionId(), session);
        return session;
    }

    /**
     * Initializes the repository with a valid, verifiable OTP session for testing.
     * This method would not exist in a production environment.
     */
    public void initializeMockSession(String sessionId, String userId) {
        OtpSession_votp_8a9b mockSession = OtpSession_votp_8a9b.builder()
            .sessionId(sessionId)
            .userId(userId)
            .otp("123456") // In production, this MUST be a secure hash.
            .expiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
            .attempts(0)
            .verified(false)
            .build();
        save(mockSession);
    }
}
```
```java