package com.example.verifyloginotpapi.service;

import com.example.verifyloginotpapi.domain.OtpDetailsVerifyLoginOtpApi;
import com.example.verifyloginotpapi.domain.UserVerifyLoginOtpApi;
import com.example.verifyloginotpapi.dto.request.VerifyOtpRequestVerifyLoginOtpApi;
import com.example.verifyloginotpapi.dto.response.VerifyOtpSuccessResponseVerifyLoginOtpApi;
import com.example.verifyloginotpapi.enums.ErrorCodeVerifyLoginOtpApi;
import com.example.verifyloginotpapi.exception.ApiExceptionVerifyLoginOtpApi;
import com.example.verifyloginotpapi.repository.IOtpRepositoryVerifyLoginOtpApi;
import com.example.verifyloginotpapi.repository.IUserRepositoryVerifyLoginOtpApi;
import com.example.verifyloginotpapi.util.StructuredLoggerVerifyLoginOtpApi;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Implements the core business logic for user authentication.
 *
 * mermaid
 * sequenceDiagram
 *     participant C as Controller
 *     participant S as AuthService
 *     participant UR as UserRepository
 *     participant OR as OtpRepository
 *     participant TS as TokenService
 *
 *     C->>+S: verifyLoginOtp(request)
 *     S->>+UR: findByPhoneNumber(phoneNumber)
 *     UR-->>-S: Optional&lt;User&gt;
 *     alt User not found
 *         S-->>C: throws USER_NOT_FOUND
 *     end
 *     S->>+OR: findByPhoneNumber(phoneNumber)
 *     OR-->>-S: Optional&lt;OtpDetails&gt;
 *     alt OTP not initiated
 *         S-->>C: throws OTP_NOT_INITIATED
 *     end
 *     S-->>S: Validate OTP (code, expiry, attempts)
 *     alt Validation Fails
 *         S->>OR: save(updatedOtpDetails)
 *         S-->>C: throws INVALID_OTP / OTP_EXPIRED / TOO_MANY_ATTEMPTS
 *     end
 *     S->>+OR: delete(phoneNumber)
 *     OR-->>-S: void
 *     S->>+TS: generateSessionToken(user)
 *     TS-->>-S: SuccessResponse
 *     S-->>-C: return SuccessResponse
 */
@Service
public class AuthServiceImplVerifyLoginOtpApi implements IAuthServiceVerifyLoginOtpApi {

    private static final int MAX_OTP_ATTEMPTS = 5;

    private final IUserRepositoryVerifyLoginOtpApi userRepository;
    private final IOtpRepositoryVerifyLoginOtpApi otpRepository;
    private final ITokenServiceVerifyLoginOtpApi tokenService;
    private final StructuredLoggerVerifyLoginOtpApi logger;

    public AuthServiceImplVerifyLoginOtpApi(IUserRepositoryVerifyLoginOtpApi userRepository,
                                            IOtpRepositoryVerifyLoginOtpApi otpRepository,
                                            ITokenServiceVerifyLoginOtpApi tokenService,
                                            StructuredLoggerVerifyLoginOtpApi logger) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.tokenService = tokenService;
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerifyOtpSuccessResponseVerifyLoginOtpApi verifyLoginOtp(VerifyOtpRequestVerifyLoginOtpApi request) {
        final String methodName = "AuthServiceImplVerifyLoginOtpApi.verifyLoginOtp";
        long startTime = System.currentTimeMillis();
        logger.logEntry(methodName, "phoneNumber", request.getPhoneNumber());

        UserVerifyLoginOtpApi user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi.USER_NOT_FOUND));

        OtpDetailsVerifyLoginOtpApi otpDetails = otpRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi.OTP_NOT_INITIATED));

        validateOtp(otpDetails, request.getOtpCode());

        // Consume the OTP by deleting it
        otpRepository.delete(request.getPhoneNumber());
        logger.logInfo(methodName, "OTP successfully verified and consumed", "phoneNumber", request.getPhoneNumber());

        VerifyOtpSuccessResponseVerifyLoginOtpApi response = tokenService.generateSessionToken(user);

        logger.logExit(methodName, System.currentTimeMillis() - startTime, "userId", user.getUserId());
        return response;
    }

    /**
     * Validates the provided OTP against the stored details, handling expiry, attempts, and code matching.
     * Throws an ApiExceptionVerifyLoginOtpApi if any validation fails.
     * This method has side effects: it increments the attempt count and saves the OTP details.
     * @param otpDetails The stored OTP details from the repository.
     * @param providedCode The OTP code from the user request.
     */
    private void validateOtp(OtpDetailsVerifyLoginOtpApi otpDetails, String providedCode) {
        // Check 1: Has the OTP expired?
        if (Instant.now().isAfter(otpDetails.getExpiresAt())) {
            otpRepository.delete(otpDetails.getPhoneNumber()); // Clean up expired OTP
            throw new ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi.OTP_EXPIRED);
        }

        // Check 2: Have there been too many attempts?
        if (otpDetails.getAttemptCount() >= MAX_OTP_ATTEMPTS) {
            otpRepository.delete(otpDetails.getPhoneNumber()); // Invalidate OTP
            throw new ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi.TOO_MANY_ATTEMPTS);
        }

        // Check 3: Does the code match?
        if (!otpDetails.getOtpCode().equals(providedCode)) {
            // Increment attempt count and save
            otpDetails.setAttemptCount(otpDetails.getAttemptCount() + 1);
            otpRepository.save(otpDetails);
            throw new ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi.INVALID_OTP);
        }
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/service/JwtTokenServiceImplVerifyLoginOtpApi.java