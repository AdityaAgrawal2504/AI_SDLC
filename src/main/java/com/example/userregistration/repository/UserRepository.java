package com.example.userregistration.repository;

import com.example.userregistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their phone number.
     * Corresponds to the IUserRepository.findByPhoneNumber contract.
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}
```
```java
// src/main/java/com/example/userregistration/service/IPasswordService.java