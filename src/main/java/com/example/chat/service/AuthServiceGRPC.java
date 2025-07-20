package com.example.chat.service;

import com.example.chat.grpc.spec.JoinRequestGRPC;
import org.springframework.stereotype.Service;

/**
 * Service to handle user authentication.
 */
public interface AuthServiceGRPC {
    /**
     * Authenticates a user based on the join request.
     * @param request The join request containing the session token.
     * @return true if authenticated, false otherwise.
     */
    boolean authenticate(JoinRequestGRPC request);
}

@Service
class MockAuthServiceGRPCImpl implements AuthServiceGRPC {
    private static final String VALID_TOKEN_PREFIX = "valid-token-for-";

    /**
     * Mocks user authentication. A token is valid if it starts with "valid-token-for-"
     * followed by the user ID from the request.
     */
    @Override
    public boolean authenticate(JoinRequestGRPC request) {
        String expectedToken = VALID_TOKEN_PREFIX + request.getUserId();
        return request.getSessionToken() != null && request.getSessionToken().equals(expectedToken);
    }
}
```
src/main/java/com/example/chat/service/ChannelPermissionServiceGRPC.java
```java