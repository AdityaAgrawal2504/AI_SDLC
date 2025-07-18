package com.example.userregistration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity representing a user in the database.
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_phone_number", columnList = "phoneNumber", unique = true)
})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
```
```java
// src/main/java/com/example/userregistration/repository/UserRepository.java