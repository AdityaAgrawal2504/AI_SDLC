package com.example.userregistration.service;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationResponse;

/**
 * Interface defining the contract for user-related business logic.
 */
public interface IUserService {
    
    /**
     * Registers a new user based on the provided data.
     */
    UserRegistrationResponse registerUser(UserRegistrationRequest registrationRequest);
}
```
```java
// src/main/java/com/example/userregistration/service/UserServiceImpl.java