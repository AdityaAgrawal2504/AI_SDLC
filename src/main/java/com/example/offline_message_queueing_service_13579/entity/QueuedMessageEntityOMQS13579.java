package com.example.offline_message_queueing_service_13579.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "queued_messages", indexes = {
    @Index(name = "idx_recipient_id", columnList = "recipientId"),
    @Index(name = "idx_enqueued_at", columnList = "enqueuedAt")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueuedMessageEntityOMQS13579 {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false, name = "recipientId")
    private UUID recipientId;

    @Lob
    @Column(nullable = false, columnDefinition = "CLOB")
    private Map<String, Object> payload;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false, name = "enqueuedAt")
    private Instant enqueuedAt;
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/repository/QueuedMessageRepositoryOMQS13579.java