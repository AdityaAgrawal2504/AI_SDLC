package com.example.auth.model;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    public User(String phoneNumber, String passwordHash, UserStatus status) {
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.status = status;
    }
}
```
src/main/java/com/example/auth/model/LoginAttempt.java
```java