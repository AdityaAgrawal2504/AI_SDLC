package com.example.auth.service;

import com.example.auth.dto.LoginInitiationRequestDTO;
import com.example.auth.dto.LoginInitiationResponseDTO;

/**
 * Service interface for handling user authentication logic.
 *
 * <!--
 * mermaid
 * classDiagram
 *      IAuthenticationService <|.. AuthenticationServiceImpl
 *      AuthenticationServiceImpl ..> IUserRepository
 *      AuthenticationServiceImpl ..> ILoginAttemptRepository
 *      AuthenticationServiceImpl ..> IOtpService
 *      AuthenticationServiceImpl ..> PasswordEncoder
 *      AuthenticationServiceImpl ..> RateLimiterService
 *
 *      class IAuthenticationService {
 *          +initiateLogin(LoginInitiationRequestDTO request) LoginInitiationResponseDTO
 *      }
 *
 *      class AuthenticationServiceImpl {
 *          -userRepository
 *          -loginAttemptRepository
 *          -otpService
 *          -passwordEncoder
 *          -rateLimiterService
 *          +initiateLogin(LoginInitiationRequestDTO request)
 *      }
 *
 *      class IUserRepository {
 *          <<interface>>
 *      }
 *      class ILoginAttemptRepository {
 *          <<interface>>
 *      }
 *      class IOtpService {
 *          <<interface>>
 *      }
 *      class PasswordEncoder {
 *          <<interface>>
 *      }
 *      class RateLimiterService {
 *      }
 * -->
 */
public interface IAuthenticationService {

    /**
     * Validates user credentials, checks account status, and if everything is valid,
     * generates and dispatches an OTP to the user's registered phone number.
     * It creates a unique login attempt record to be used in the next step of verification.
     *
     * @param request The DTO containing the user's phone number and password.
     * @return A DTO containing a success message and the unique ID for this login attempt.
     * @throws com.example.auth.exception.InvalidCredentialsException If credentials do not match.
     * @throws com.example.auth.exception.AccountLockedException If the user's account is locked.
     * @throws com.example.auth.exception.OtpServiceException If the OTP provider fails.
     * @throws com.example.auth.exception.RateLimitException If too many login attempts are made.
     */
    LoginInitiationResponseDTO initiateLogin(LoginInitiationRequestDTO request);
}
```
src/main/java/com/example/auth/service/impl/AuthenticationServiceImpl.java
```java