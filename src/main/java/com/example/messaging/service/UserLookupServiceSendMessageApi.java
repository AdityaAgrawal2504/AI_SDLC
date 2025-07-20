package com.example.messaging.service;

import com.example.messaging.exception.RecipientNotFoundExceptionSendMessageApi;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;

/**
 * Mock service to simulate looking up users.
 */
@Service
public class UserLookupServiceSendMessageApi {

    // A dummy set of valid recipient UUIDs
    private static final Set<String> VALID_RECIPIENTS = Set.of(
        "a1b2c3d4-e5f6-7890-1234-567890abcdef",
        "11223344-5566-7788-9900-aabbccddeeff"
    );

    /**
     * Simulates finding a user by their ID.
     */
    public void findUserById(String recipientId) {
        try {
            UUID.fromString(recipientId); // Pre-validate format
            if (!VALID_RECIPIENTS.contains(recipientId)) {
                throw new RecipientNotFoundExceptionSendMessageApi("The specified recipient could not be found.");
            }
        } catch (IllegalArgumentException e) {
            // This case should be caught by validation, but is here as a safeguard.
            throw new RecipientNotFoundExceptionSendMessageApi("The specified recipient ID is not a valid UUID.");
        }
    }
}
```
```java