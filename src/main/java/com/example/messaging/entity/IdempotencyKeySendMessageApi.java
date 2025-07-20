package com.example.messaging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * JPA Entity for tracking idempotency keys to prevent duplicate requests.
 */
@Entity
@Table(name = "idempotency_keys", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotencyKey", unique = true)
})
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class IdempotencyKeySendMessageApi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID idempotencyKey;

    @Column(nullable = false)
    private String requestHash;

    @Column(nullable = false)
    private int responseStatus;

    @Column(nullable = false, length = 2048)
    private String responseBody;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
```
```java