package com.example.logininitiation.service;

import com.example.logininitiation.model.User_LIAPI_5001;

/**
 * Manages user data retrieval and validation.
 */
public interface IUserService_LIAPI_6001 {
    
    /**
     * Finds a user by phone number and validates their password hash.
     * Throws an error if not found or password mismatch.
     * @param phoneNumber The user's phone number.
     * @param rawPassword The user's plaintext password.
     * @return The validated User object.
     */
    User_LIAPI_5001 findAndValidateCredentials(String phoneNumber, String rawPassword);
}
```
```java
// src/main/java/com/example/logininitiation/service/impl/AuthenticationServiceImpl_LIAPI_7003.java