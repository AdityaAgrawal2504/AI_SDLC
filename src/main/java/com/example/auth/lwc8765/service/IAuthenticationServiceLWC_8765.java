package com.example.auth.lwc8765.service;

import com.example.auth.lwc8765.dto.request.LoginCredentialsRequestLWC_8765;
import com.example.auth.lwc8765.dto.response.LoginInitiatedResponseLWC_8765;

public interface IAuthenticationServiceLWC_8765 {
    /**
     * Authenticates user credentials, sends an OTP, and returns a temporary token.
     * @param request The login request containing phone number and password.
     * @return A response containing the temporary session token.
     */
    LoginInitiatedResponseLWC_8765 initiateLogin(LoginCredentialsRequestLWC_8765 request);
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/AuthenticationServiceLWC_8765.java