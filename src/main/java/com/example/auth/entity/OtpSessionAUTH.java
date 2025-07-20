package com.example.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * JPA entity to store OTP session details for two-factor authentication.
 */
@Entity
@Table(name = "otp_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpSessionAUTH {
    @Id
    private String sessionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private LocalDateTime expiryTimestamp;

    private boolean verified = false;
}
```
```java
// src/main/java/com/example/auth/repository/UserRepositoryAUTH.java