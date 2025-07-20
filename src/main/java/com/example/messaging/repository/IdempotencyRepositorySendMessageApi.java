package com.example.messaging.repository;

import com.example.messaging.entity.IdempotencyKeySendMessageApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the IdempotencyKeySendMessageApi entity.
 */
@Repository
public interface IdempotencyRepositorySendMessageApi extends JpaRepository<IdempotencyKeySendMessageApi, UUID> {

    /**
     * Finds an idempotency record by its key.
     */
    Optional<IdempotencyKeySendMessageApi> findByIdempotencyKey(UUID idempotencyKey);
}
```
```java