package com.example.messaging.service;

import com.example.messaging.exception.UnauthorizedExceptionSendMessageApi;
import org.springframework.stereotype.Service;

/**
 * Mock service to simulate authentication logic.
 */
@Service
public class AuthenticationServiceSendMessageApi {

    /**
     * Simulates validating a bearer token.
     * In a real application, this would involve JWT parsing and validation.
     * @return The ID of the authenticated sender.
     */
    public String validateTokenAndGetSenderId(String token) {
        if (token == null || !token.startsWith("Bearer ") || token.length() < 10) {
            throw new UnauthorizedExceptionSendMessageApi("Authentication credentials are required and are missing or invalid.");
        }
        // Dummy logic: return a fixed sender ID
        return "c1d2e3f4-a5b6-7890-1234-567890abcdef";
    }
}
```
```java