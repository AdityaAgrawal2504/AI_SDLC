package com.example.auth.verifyotp.service;

import com.example.auth.verifyotp.config.ErrorCode_votp_8a9b;
import com.example.auth.verifyotp.dto.VerifyOtpRequest_votp_8a9b;
import com.example.auth.verifyotp.dto.VerifyOtpResponse_votp_8a9b;
import com.example.auth.verifyotp.exception.OtpVerificationException_votp_8a9b;
import com.example.auth.verifyotp.logging.LogExecution_votp_8a9b;
import com.example.auth.verifyotp.logging.StructuredLogger_votp_8a9b;
import com.example.auth.verifyotp.model.OtpSession_votp_8a9b;
import com.example.auth.verifyotp.repository.OtpSessionRepository_votp_8a9b;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service implementation containing the core business logic for OTP verification.
 *
 * Mermaid.js Diagram:
 *
 * flowchart TD
 *     A[POST /auth/verify-otp] --> B{VerifyOtpServiceImpl};
 *     B --> C{Parse Session Token};
 *     C -- Invalid Token --> F[Throw SESSION_NOT_FOUND];
 *     C -- Valid Token --> D{Fetch Session from Repo};
 *     D -- Not Found --> F;
 *     D -- Found --> E{Validate Session};
 *     subgraph Validate Session
 *        E1[Is Expired?] -- Yes --> G[Throw OTP_EXPIRED];
 *        E2[Is Verified?] -- Yes --> F;
 *        E3[Max Attempts?] -- Yes --> H[Throw MAX_ATTEMPTS_REACHED];
 *        E4[OTP Match?] -- No --> I[Increment Attempts & Throw INVALID_OTP];
 *     end
 *     E -- All Validations Pass --> J{Mark Session Verified};
 *     J --> K{Generate Access Token};
 *     K -- Failed --> L[Throw TOKEN_GENERATION_FAILED];
 *     K -- Success --> M[Return VerifyOtpResponse];
 *     A --> M;
 */
@Service
public class OtpVerificationServiceImpl_votp_8a9b implements OtpVerificationService_votp_8a9b {

    private final OtpSessionRepository_votp_8a9b sessionRepository;
    private final JwtService_votp_8a9b jwtService;
    private final StructuredLogger_votp_8a9b logger;

    @Value("${otp.max-attempts}")
    private int maxAttempts;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpirationSeconds;

    /**
     * Constructs the service with required dependencies.
     * @param sessionRepository Repository for accessing OTP session data.
     * @param jwtService Service for handling JWT operations.
     * @param logger Structured logger for application logging.
     */
    public OtpVerificationServiceImpl_votp_8a9b(OtpSessionRepository_votp_8a9b sessionRepository, JwtService_votp_8a9b jwtService, StructuredLogger_votp_8a9b logger) {
        this.sessionRepository = sessionRepository;
        this.jwtService = jwtService;
        this.logger = logger;
    }
    
    /**
     * Initializes a mock session for demonstration purposes.
     * In a real application, OTP generation would be a separate endpoint/process.
     */
    @PostConstruct
    public void createMockData() {
        String mockSessionId = "ce42fc58-bf37-492a-90cf-ce194ddb716c";
        String mockUserId = "user-12345";
        sessionRepository.initializeMockSession(mockSessionId, mockUserId);
        
        String sessionToken = jwtService.createSessionToken(mockSessionId);
        logger.logInfo("Mock session created for testing", Map.of(
            "userId", mockUserId,
            "otp", "123456",
            "sessionToken", sessionToken
        ));
    }


    /**
     * Verifies the OTP and generates an access token upon success.
     * @param request The request containing the session token and OTP.
     * @return A response DTO with the access token.
     */
    @Override
    @LogExecution_votp_8a9b
    public VerifyOtpResponse_votp_8a9b verifyOtp(VerifyOtpRequest_votp_8a9b request) {
        String sessionId = extractSessionId(request.getSessionToken());
        
        try {
            OtpSession_votp_8a9b session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.SESSION_NOT_FOUND));

            validateSessionState(session);
            validateOtp(session, request.getOtp());

            session.setVerified(true);
            sessionRepository.save(session);

            return createSuccessResponse(session.getUserId());
        } catch (OtpVerificationException_votp_8a9b e) {
            throw e;
        } catch (Exception e) {
            // Catching potential database errors.
            logger.logError("Database error during OTP verification", Map.of("sessionId", sessionId), e);
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.DATABASE_ERROR);
        }
    }

    /**
     * Extracts the session ID from the provided session token JWT.
     * @param sessionToken The JWT session token.
     * @return The extracted session ID.
     */
    private String extractSessionId(String sessionToken) {
        try {
            return jwtService.getSessionIdFromToken(sessionToken);
        } catch (JwtException | IllegalArgumentException e) {
            logger.logWarn("Invalid session token received", Map.of("token", sessionToken, "error", e.getMessage()));
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.SESSION_NOT_FOUND);
        }
    }

    /**
     * Validates the current state of the OTP session (expiry, attempts, verified status).
     * @param session The OTP session to validate.
     */
    private void validateSessionState(OtpSession_votp_8a9b session) {
        if (session.isVerified()) {
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.SESSION_NOT_FOUND);
        }
        if (session.isExpired()) {
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.OTP_EXPIRED);
        }
        if (session.getAttempts() >= maxAttempts) {
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.MAX_ATTEMPTS_REACHED);
        }
    }

    /**
     * Validates the provided OTP against the one stored in the session.
     * @param session The OTP session containing the correct OTP.
     * @param providedOtp The OTP submitted by the user.
     */
    private void validateOtp(OtpSession_votp_8a9b session, String providedOtp) {
        // NOTE: In production, use a secure, constant-time comparison of hashes.
        if (!session.getOtp().equals(providedOtp)) {
            session.incrementAttempts();
            sessionRepository.save(session);
            logger.logWarn("Invalid OTP attempt", Map.of("sessionId", session.getSessionId(), "attempts", session.getAttempts()));
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.INVALID_OTP);
        }
    }

    /**
     * Creates the final success response containing the long-lived access token.
     * @param userId The ID of the authenticated user.
     * @return A DTO with the access token details.
     */
    private VerifyOtpResponse_votp_8a9b createSuccessResponse(String userId) {
        try {
            String accessToken = jwtService.createAccessToken(userId, new HashMap<>());
            
            logger.logInfo("Access token generated successfully", Map.of("userId", userId));

            return VerifyOtpResponse_votp_8a9b.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpirationSeconds)
                .build();
        } catch (Exception e) {
            logger.logError("Failed to generate access token", Map.of("userId", userId), e);
            throw new OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b.TOKEN_GENERATION_FAILED);
        }
    }
}
```