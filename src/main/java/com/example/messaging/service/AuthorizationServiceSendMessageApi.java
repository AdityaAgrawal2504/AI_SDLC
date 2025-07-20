package com.example.messaging.service;

import com.example.messaging.exception.PermissionDeniedExceptionSendMessageApi;
import org.springframework.stereotype.Service;

/**
 * Mock service to simulate authorization logic.
 */
@Service
public class AuthorizationServiceSendMessageApi {
    
    /**
     * Simulates checking if a sender is allowed to message a recipient.
     */
    public void checkPermission(String senderId, String recipientId) {
        // Dummy logic: Deny if recipientId is a specific "blocked" user
        if ("f4e3d2c1-b6a5-0987-4321-fedcba098765".equals(recipientId)) {
            throw new PermissionDeniedExceptionSendMessageApi("The authenticated user is not permitted to send messages to this recipient.");
        }
        // Allow all other combinations
    }
}
```
```java