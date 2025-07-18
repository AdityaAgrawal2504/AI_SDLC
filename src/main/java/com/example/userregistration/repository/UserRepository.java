package com.example.userregistration.repository;

import com.example.userregistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their phone number.
     * @param phoneNumber The phone number to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}

// src/main/java/com/example/userregistration/exception/UserConflictException.java