package com.example.messaging.entity;

import com.example.messaging.enums.MessageStatusSendMessageApi;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * JPA Entity representing a stored message.
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MessageSendMessageApi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID recipientId;

    @Column(nullable = false, length = 10000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatusSendMessageApi status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}
```
```java