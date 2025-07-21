package com.chatapp.fmh_8721.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity representing a single message.
 */
@Entity
@Table(name = "messages", indexes = {
    @Index(name = "idx_message_conversation_created", columnList = "conversationId, createdAt DESC")
})
@Getter
@Setter
public class MessageEntityFMH_8721 {

    @Id
    private String id; // Using string for CUID

    @Column(nullable = false)
    private UUID conversationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntityFMH_8721 author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageContentTypeFMH_8721 contentType;

    @Lob
    private String textContent; // For TEXT type

    private String fileUrl; // For IMAGE or FILE type
    private String fileName; // For FILE type

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatusFMH_8721 status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
```
```java
// src/main/java/com/chatapp/fmh_8721/exception/ErrorCodeFMH_8721.java