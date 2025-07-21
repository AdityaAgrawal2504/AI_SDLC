package com.example.auth.lwc8765.repository;

import com.example.auth.lwc8765.model.UserLWC_8765;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepositoryLWC_8765 extends JpaRepository<UserLWC_8765, Long> {
    /**
     * Finds a user by their unique phone number.
     * @param phoneNumber The 10-digit phone number.
     * @return An Optional containing the user if found.
     */
    Optional<UserLWC_8765> findByPhoneNumber(String phoneNumber);
}
```
```java
// src/main/java/com/example/auth/lwc8765/exception/CustomAuthenticationExceptionLWC_8765.java