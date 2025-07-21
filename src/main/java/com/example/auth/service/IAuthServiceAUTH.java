package com.example.auth.service;

import com.example.auth.dto.LoginRequestDTOAUTH;
import com.example.auth.dto.LoginSuccessResponseDTOAUTH;

import java.util.concurrent.ExecutionException;

/**
 * Defines the contract for the authentication business logic.
 */
public interface IAuthServiceAUTH {
    /**
     * Handles the core logic of validating credentials and dispatching an OTP.
     * @param request The login request DTO containing user credentials.
     * @return A DTO with a success message and transaction ID.
     * @throws ExecutionException If the async OTP sending task fails.
     * @throws InterruptedException If the async OTP sending task is interrupted.
     */
    LoginSuccessResponseDTOAUTH requestLoginOtp(LoginRequestDTOAUTH request) throws ExecutionException, InterruptedException;
}
```
```java
// src/main/java/com/example/auth/service/AuthServiceImplAUTH.java