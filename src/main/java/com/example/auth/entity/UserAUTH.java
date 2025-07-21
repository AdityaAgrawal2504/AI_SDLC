package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserAUTH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean locked = false;

    @Column(nullable = false)
    private int failedLoginAttempts = 0;

    public UserAUTH(String phoneNumber, String passwordHash) {
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
    }
}
```
```java
// src/main/java/com/example/auth/repository/IUserRepositoryAUTH.java