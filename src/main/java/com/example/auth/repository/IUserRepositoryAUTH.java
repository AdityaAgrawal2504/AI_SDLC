package com.example.auth.repository;

import com.example.auth.entity.UserAUTH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Defines the contract for user data persistence and retrieval.
 */
@Repository
public interface IUserRepositoryAUTH extends JpaRepository<UserAUTH, Long> {

    /**
     * Retrieves a user entity from the data store by their phone number.
     * @param phoneNumber The user's 10-digit phone number.
     * @return An Optional containing the UserAUTH if found, otherwise empty.
     */
    Optional<UserAUTH> findByPhoneNumber(String phoneNumber);
}
```
```java
// src/main/java/com/example/auth/exception/ErrorCodeAUTH.java