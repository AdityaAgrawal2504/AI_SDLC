package com.example.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * JPA entity representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
public class UserAUTH {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    private boolean accountLocked = false;
    private int failedLoginAttempts = 0;
}
```
```java
// src/main/java/com/example/auth/entity/OtpSessionAUTH.java