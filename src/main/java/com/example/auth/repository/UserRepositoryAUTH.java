package com.example.auth.repository;

import com.example.auth.entity.UserAUTH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for UserAUTH entities.
 */
@Repository
public interface UserRepositoryAUTH extends JpaRepository<UserAUTH, Long> {
    /**
     * Finds a user by their 10-digit phone number.
     */
    Optional<UserAUTH> findByPhoneNumber(String phoneNumber);
}
```
```java
// src/main/java/com/example/auth/repository/OtpSessionRepositoryAUTH.java