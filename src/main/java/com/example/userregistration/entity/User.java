package com.example.userregistration.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a user entity in the database.
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /**
     * The unique identifier for the user (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The user's phone number, which must be unique.
     */
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    /**
     * The user's encrypted password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The timestamp when the user entity was created.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    /**
     * The timestamp when the user entity was last updated.
     */
    @UpdateTimestamp
    private Instant updatedAt;
}

// src/main/java/com/example/userregistration/repository/UserRepository.java