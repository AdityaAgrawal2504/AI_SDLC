package com.example.offline_message_queueing_service_13579.service;

import com.example.offline_message_queueing_service_13579.constants.ErrorCodeOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.EnqueueResponseDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.MessageDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.entity.QueuedMessageEntityOMQS13579;
import com.example.offline_message_queueing_service_13579.exception.PersistenceExceptionOMQS13579;
import com.example.offline_message_queueing_service_13579.exception.ResourceNotFoundExceptionOMQS13579;
import com.example.offline_message_queueing_service_13579.exception.ValidationExceptionOMQS13579;
import com.example.offline_message_queueing_service_13579.repository.QueuedMessageRepositoryOMQS13579;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageQueueServiceImplOMQS13579 implements MessageQueueServiceOMQS13579 {

    private final QueuedMessageRepositoryOMQS13579 messageRepository;

    /**
     * Validates and persists a message for an offline user.
     */
    @Override
    public EnqueueResponseDtoOMQS13579 enqueueMessage(UUID userId, MessageDtoOMQS13579 messageDto) {
        if (!userId.equals(messageDto.recipientId())) {
            throw new ValidationExceptionOMQS13579(
                ErrorCodeOMQS13579.USER_ID_MISMATCH,
                "Recipient ID in message body must match user ID in URL path."
            );
        }
        
        QueuedMessageEntityOMQS13579 entity = QueuedMessageEntityOMQS13579.builder()
                .senderId(messageDto.senderId())
                .recipientId(messageDto.recipientId())
                .payload(messageDto.payload())
                .timestamp(messageDto.timestamp())
                .enqueuedAt(Instant.now())
                .build();
        try {
            QueuedMessageEntityOMQS13579 savedEntity = messageRepository.save(entity);
            return EnqueueResponseDtoOMQS13579.builder()
                    .messageId(savedEntity.getMessageId())
                    .enqueuedAt(savedEntity.getEnqueuedAt())
                    .build();
        } catch (Exception e) {
            throw new PersistenceExceptionOMQS13579(ErrorCodeOMQS13579.PERSISTENCE_FAILURE, "Failed to persist message.", e);
        }
    }

    /**
     * Retrieves all queued messages for a given user, up to the specified limit.
     */
    @Override
    @Transactional(readOnly = true)
    public Map<UUID, MessageDtoOMQS13579> dequeueMessages(UUID userId, int limit) {
        try {
            List<QueuedMessageEntityOMQS13579> messages = messageRepository.findByRecipientIdOrderByEnqueuedAtAsc(userId, PageRequest.of(0, limit));
            return messages.stream()
                    .map(this::mapEntityToDto)
                    .collect(Collectors.toMap(MessageDtoOMQS13579::messageId, Function.identity()));
        } catch (Exception e) {
            throw new PersistenceExceptionOMQS13579(ErrorCodeOMQS13579.PERSISTENCE_FAILURE, "Failed to retrieve messages.", e);
        }
    }

    /**
     * Acknowledges and deletes a set of messages for a user.
     */
    @Override
    @Transactional
    public void acknowledgeMessages(UUID userId, Set<UUID> messageIds) {
        if (messageIds.isEmpty()) {
            return;
        }

        try {
            List<QueuedMessageEntityOMQS13579> existingMessages = messageRepository.findAllByRecipientIdAndMessageIdIn(userId, messageIds);

            if (existingMessages.size() != messageIds.size()) {
                throw new ResourceNotFoundExceptionOMQS13579("One or more specified message IDs do not exist in the user's queue.");
            }
            
            messageRepository.deleteAllByMessageIdIn(messageIds);
        } catch (ResourceNotFoundExceptionOMQS13579 e) {
            throw e; // re-throw to be handled by controller advice
        } catch (Exception e) {
            throw new PersistenceExceptionOMQS13579(ErrorCodeOMQS13579.DELETION_FAILURE, "Failed to remove acknowledged messages.", e);
        }
    }

    /**
     * Scheduled job to automatically purge messages older than 7 days.
     */
    @Override
    public void purgeOldMessages() {
        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        try {
            long deletedCount = messageRepository.deleteByEnqueuedAtBefore(sevenDaysAgo);
            if (deletedCount > 0) {
                log.info("Successfully purged {} old messages from the queue.", deletedCount);
            }
        } catch (Exception e) {
            log.error("Scheduled purge of old messages failed.", e);
        }
    }

    private MessageDtoOMQS13579 mapEntityToDto(QueuedMessageEntityOMQS13579 entity) {
        return MessageDtoOMQS13579.builder()
                .messageId(entity.getMessageId())
                .senderId(entity.getSenderId())
                .recipientId(entity.getRecipientId())
                .payload(entity.getPayload())
                .timestamp(entity.getTimestamp())
                .enqueuedAt(entity.getEnqueuedAt())
                .build();
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/controller/MessageQueueControllerOMQS13579.java