package com.example.fetchconversationsapi1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Represents a single message within a conversation.
 */
@Entity
@Table(name = "message")
@Getter
@Setter
public class MessageEntity_FCAPI_1 {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity_FCAPI_1 conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity_FCAPI_1 sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/model/ConversationParticipantEntity_FCAPI_1.java