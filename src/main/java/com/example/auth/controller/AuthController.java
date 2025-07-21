package com.example.auth.controller;

import com.example.auth.dto.LoginRequestDTOAUTH;
import com.example.auth.dto.LoginSuccessResponseDTOAUTH;
import com.example.auth.service.IAuthServiceAUTH;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Controller for handling authentication-related API endpoints.
 *
 * mermaid
 * sequenceDiagram
 *   Client->>+Server: POST /auth/login (credentials)
 *   Server->>+AuthService: requestLoginOtp(credentials)
 *   AuthService->>+UserRepository: findByPhoneNumber()
 *   UserRepository-->>-AuthService: User object or null
 *   alt Credentials invalid
 *     AuthService-->>-Server: Throw InvalidCredentialsException
 *     Server-->>-Client: 401 Unauthorized
 *   else Credentials valid
 *     AuthService->>+OTPService: generate()
 *     OTPService-->>-AuthService: OTP code
 *     AuthService->>+Cache: store(transactionId, otp)
 *     Cache-->>-AuthService: OK
 *     AuthService->>+OTPService: send(phoneNumber, otp)
 *     OTPService-->>-AuthService: Future<Boolean>
 *     alt OTP send fails
 *       AuthService-->>-Server: Throw OTPServiceFailureException
 *       Server-->>-Client: 500 Internal Server Error
 *     else OTP send succeeds
 *       AuthService-->>-Server: LoginSuccessResponseDTO
 *       Server-->>-Client: 200 OK (message, transactionId)
 *     end
 *   end
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthServiceAUTH authService;

    public AuthController(IAuthServiceAUTH authService) {
        this.authService = authService;
    }

    /**
     * Handles the POST request to /auth/login.
     * Validates credentials and initiates the OTP sending process.
     * @param loginRequest The request body containing phone number and password.
     * @return A ResponseEntity containing the success response or an error.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginSuccessResponseDTOAUTH> requestLoginOtp(@Valid @RequestBody LoginRequestDTOAUTH loginRequest) 
            throws ExecutionException, InterruptedException {
        LoginSuccessResponseDTOAUTH response = authService.requestLoginOtp(loginRequest);
        return ResponseEntity.ok(response);
    }
}
```