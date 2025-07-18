package com.example.userregistration.service;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationSuccessResponse;
import com.example.userregistration.entity.User;
import com.example.userregistration.exception.UserConflictException;
import com.example.userregistration.exception.ServiceException;
import com.example.userregistration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// MERMAID-DIAGRAM-START
/*
// This Mermaid.js sequence diagram illustrates the user registration process.

sequenceDiagram
    participant C as Client
    participant API as Controller
    participant SVC as Service
    participant REPO as Repository
    participant PWD as PasswordEncoder
    participant DB as Database

    C->>+API: POST /register (phoneNumber, password)
    API->>+SVC: registerUser(request)
    SVC->>+REPO: findByPhoneNumber(phoneNumber)
    REPO->>+DB: SELECT * FROM users WHERE phone_number = ?
    DB-->>-REPO: User record (or null)
    alt User Exists
        REPO-->>SVC: Optional[User]
        SVC-->>-API: throws UserConflictException
        API-->>-C: 409 Conflict Response
    else User Does Not Exist
        REPO-->>SVC: Optional.empty
        SVC->>+PWD: encode(rawPassword)
        PWD-->>-SVC: hashedPassword
        SVC->>SVC: Create User entity
        SVC->>+REPO: save(user)
        REPO->>+DB: INSERT INTO users (...)
        DB-->>-REPO: Saved User with ID
        REPO-->>SVC: Saved User entity
        SVC->>SVC: Create SuccessResponse
        SVC-->>-API: UserRegistrationSuccessResponse
        API-->>-C: 201 Created Response
    end
*/
// MERMAID-DIAGRAM-END

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements IUserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user after validating that the phone number is not already in use.
     * It hashes the password before persisting the new user record.
     */
    @Override
    @Transactional
    public UserRegistrationSuccessResponse registerUser(UserRegistrationRequest request) {
        try {
            userRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(user -> {
                throw new UserConflictException("A user with this phone number is already registered.");
            });

            User newUser = new User();
            newUser.setPhoneNumber(request.getPhoneNumber());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            User savedUser = userRepository.save(newUser);

            return new UserRegistrationSuccessResponse(
                savedUser.getId(),
                "User registered successfully."
            );
        } catch (UserConflictException e) {
            throw e; // Re-throw to be handled by the global exception handler
        } catch (Exception e) {
            // Catches persistence errors or other unexpected issues
            throw new ServiceException("Failed to register user due to a server issue.", e);
        }
    }
}


// src/main/java/com/example/userregistration/controller/UserRegistrationController.java