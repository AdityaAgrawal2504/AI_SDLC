package com.example.userregistration.service;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationSuccessResponse;
import com.example.userregistration.exception.UserConflictException;
import com.example.userregistration.exception.ServiceException;

/**
 * Defines the business logic contract for handling user registration.
 */
public interface IUserRegistrationService {

    /**
     * Validates input, creates a new user, and persists it.
     * @param request The user registration request data.
     * @return The successful registration response containing the new user's ID.
     * @throws UserConflictException Thrown when a user with the given phone number already exists.
     * @throws ServiceException Thrown for general service-level errors.
     */
    UserRegistrationSuccessResponse registerUser(UserRegistrationRequest request);
}

// src/main/java/com/example/userregistration/service/UserRegistrationServiceImpl.java