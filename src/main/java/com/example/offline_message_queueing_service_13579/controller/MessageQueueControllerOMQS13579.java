package com.example.offline_message_queueing_service_13579.controller;

import com.example.offline_message_queueing_service_13579.dto.EnqueueResponseDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.MessageAcknowledgementDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.MessageDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.service.MessageQueueServiceOMQS13579;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/queues")
@RequiredArgsConstructor
@Validated
public class MessageQueueControllerOMQS13579 {

    private final MessageQueueServiceOMQS13579 messageQueueService;

    /**
     * Intercepts and queues a single message for a specified offline user.
     */
    @PostMapping("/{userId}/messages")
    public ResponseEntity<EnqueueResponseDtoOMQS13579> enqueueMessage(
            @PathVariable UUID userId,
            @Valid @RequestBody MessageDtoOMQS13579 messageDto) {
        EnqueueResponseDtoOMQS13579 response = messageQueueService.enqueueMessage(userId, messageDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves all queued messages for a user, typically when they come online.
     */
    @GetMapping("/{userId}/messages")
    public ResponseEntity<Map<UUID, MessageDtoOMQS13579>> dequeueMessagesForUser(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "100") @Min(1) @Max(500) int limit) {
        Map<UUID, MessageDtoOMQS13579> messages = messageQueueService.dequeueMessages(userId, limit);
        return ResponseEntity.ok(messages);
    }

    /**
     * Acknowledges that messages have been received, removing them from the queue.
     */
    @PostMapping("/{userId}/acknowledgements")
    public ResponseEntity<Void> acknowledgeMessages(
            @PathVariable UUID userId,
            @Valid @RequestBody MessageAcknowledgementDtoOMQS13579 acknowledgementDto) {
        messageQueueService.acknowledgeMessages(userId, acknowledgementDto.messageIds().keySet());
        return ResponseEntity.noContent().build();
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/config/HealthCheckConfigOMQS13579.java