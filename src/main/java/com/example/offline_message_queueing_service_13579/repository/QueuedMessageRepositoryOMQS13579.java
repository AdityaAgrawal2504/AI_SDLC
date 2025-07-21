package com.example.offline_message_queueing_service_13579.repository;

import com.example.offline_message_queueing_service_13579.entity.QueuedMessageEntityOMQS13579;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface QueuedMessageRepositoryOMQS13579 extends JpaRepository<QueuedMessageEntityOMQS13579, UUID> {

    /**
     * Finds all messages for a specific recipient, ordered by enqueue time.
     */
    List<QueuedMessageEntityOMQS13579> findByRecipientIdOrderByEnqueuedAtAsc(UUID recipientId, Pageable pageable);

    /**
     * Finds all messages for a recipient that match a given set of message IDs.
     */
    List<QueuedMessageEntityOMQS13579> findAllByRecipientIdAndMessageIdIn(UUID recipientId, Collection<UUID> messageIds);

    /**
     * Deletes all messages by their IDs.
     */
    @Transactional
    void deleteAllByMessageIdIn(Collection<UUID> messageIds);
    
    /**
     * Deletes all messages that were enqueued before a specified timestamp.
     */
    @Transactional
    long deleteByEnqueuedAtBefore(Instant cutoff);
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/dto/MessageDtoOMQS13579.java