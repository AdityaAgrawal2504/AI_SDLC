package com.example.fetchconversationsapi1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents a conversation between two or more users.
 */
@Entity
@Table(name = "conversation")
@Getter
@Setter
public class ConversationEntity_FCAPI_1 {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConversationParticipantEntity_FCAPI_1> participants;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY)
    @OrderBy("createdAt DESC")
    private List<MessageEntity_FCAPI_1> messages;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message_id")
    private MessageEntity_FCAPI_1 lastMessage;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/repository/ConversationRepository_FCAPI_1.java