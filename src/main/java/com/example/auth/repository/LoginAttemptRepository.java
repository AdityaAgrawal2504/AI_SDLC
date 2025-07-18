package com.example.auth.repository;

import com.example.auth.model.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoginAttempt entity.
 */
@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, String> {
}
```
src/main/java/com/example/auth/exception/ErrorCode.java
```java