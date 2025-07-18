package com.example.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity to store details of a login attempt, linking it to an OTP.
 */
@Entity
@Table(name = "login_attempts")
@Data
@NoArgsConstructor
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String otpCodeHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private boolean verified = false;

    public LoginAttempt(String userId, String otpCodeHash, LocalDateTime expiresAt) {
        this.userId = userId;
        this.otpCodeHash = otpCodeHash;
        this.expiresAt = expiresAt;
    }
}
```
src/main/java/com/example/auth/repository/UserRepository.java
```java