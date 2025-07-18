package com.example.auth.repository;

import com.example.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their phone number.
     *
     * @param phoneNumber The E.164 formatted phone number.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}
```
src/main/java/com/example/auth/repository/LoginAttemptRepository.java
```java