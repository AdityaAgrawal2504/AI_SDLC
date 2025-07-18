package com.example.userregistration.service;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationResponse;
import com.example.userregistration.entity.User;
import com.example.userregistration.exception.UserAlreadyExistsException;
import com.example.userregistration.repository.UserRepository;
import com.example.userregistration.util.logging.StructuredLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for user registration business logic.
 *
 * mermaid-js
 * sequenceDiagram
 *     participant C as UserRegistrationController
 *     participant S as UserServiceImpl
 *     participant P as IPasswordService
 *     participant R as UserRepository
 *
 *     C->>+S: registerUser(request)
 *     S->>R: findByPhoneNumber(phoneNumber)
 *     alt User Exists
 *         R-->>S: Optional.of(user)
 *         S-->>-C: throws UserAlreadyExistsException
 *     else User Not Found
 *         R-->>S: Optional.empty()
 *         S->>P: hashPassword(plainTextPassword)
 *         P-->>S: hashedPassword
 *         S->>R: save(newUser)
 *         R-->>S: savedUser
 *         S-->>-C: returns UserRegistrationResponse
 *     end
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final IPasswordService passwordService;
    private final StructuredLogger logger = StructuredLogger.getLogger(UserServiceImpl.class);

    /**
     * Registers a new user, ensuring the phone number is unique and hashing the password.
     */
    @Override
    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        final String methodName = "registerUser";
        long startTime = System.currentTimeMillis();
        logger.logStart(methodName, "phoneNumber", request.getPhoneNumber());

        try {
            userRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(user -> {
                throw new UserAlreadyExistsException("A user with phone number " + request.getPhoneNumber() + " already exists.");
            });

            String hashedPassword = passwordService.hashPassword(request.getPassword());

            User newUser = new User();
            newUser.setPhoneNumber(request.getPhoneNumber());
            newUser.setPasswordHash(hashedPassword);

            User savedUser = userRepository.save(newUser);
            logger.logInfo(methodName, "User created successfully with ID: " + savedUser.getId());

            return UserRegistrationResponse.builder()
                .userId(savedUser.getId())
                .message("User registered successfully.")
                .createdAt(savedUser.getCreatedAt())
                .build();
        } finally {
            logger.logEnd(methodName, System.currentTimeMillis() - startTime);
        }
    }
}
```
```java
// src/main/java/com/example/userregistration/controller/UserRegistrationController.java