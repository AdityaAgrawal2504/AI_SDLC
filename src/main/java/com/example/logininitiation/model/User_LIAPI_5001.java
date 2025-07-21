package com.example.logininitiation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_LIAPI_5001 {
    /**
     * User's 10-digit phone number, serving as the primary key.
     */
    @Id
    @Column(length = 10, nullable = false, unique = true)
    private String phoneNumber;

    /**
     * Hashed password for the user.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * Flag indicating if the user's account is active and can log in.
     */
    @Column(nullable = false)
    private boolean active;
}
```
```java
// src/main/java/com/example/logininitiation/repository/UserRepository_LIAPI_5002.java