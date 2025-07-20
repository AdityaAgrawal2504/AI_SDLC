package com.example.messaging.service;

import com.example.messaging.dto.SendMessageRequestSendMessageApi;
import com.example.messaging.dto.SendMessageResponseSendMessageApi;
import com.example.messaging.entity.IdempotencyKeySendMessageApi;
import com.example.messaging.entity.MessageSendMessageApi;
import com.example.messaging.enums.MessageStatusSendMessageApi;
import com.example.messaging.exception.IdempotencyConflictExceptionSendMessageApi;
import com.example.messaging.exception.ServiceUnavailableExceptionSendMessageApi;
import com.example.messaging.repository.IdempotencyRepositorySendMessageApi;
import com.example.messaging.repository.MessageRepositorySendMessageApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

/**
 * Service to manage core message processing and persistence logic.
 */
@Service
public class MessageServiceSendMessageApi {

    private final MessageRepositorySendMessageApi messageRepository;
    private final IdempotencyRepositorySendMessageApi idempotencyRepository;
    private final AuthenticationServiceSendMessageApi authService;
    private final UserLookupServiceSendMessageApi userLookupService;
    private final AuthorizationServiceSendMessageApi authorizationService;
    private final MessageQueueProducerSendMessageApi messageQueue;
    private final ObjectMapper objectMapper;

    public MessageServiceSendMessageApi(MessageRepositorySendMessageApi messageRepository,
                                        IdempotencyRepositorySendMessageApi idempotencyRepository,
                                        AuthenticationServiceSendMessageApi authService,
                                        UserLookupServiceSendMessageApi userLookupService,
                                        AuthorizationServiceSendMessageApi authorizationService,
                                        MessageQueueProducerSendMessageApi messageQueue, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.authService = authService;
        this.userLookupService = userLookupService;
        this.authorizationService = authorizationService;
        this.messageQueue = messageQueue;
        this.objectMapper = objectMapper;
    }

    /**
     * Orchestrates the entire process of sending a message, including all checks.
     */
    @Transactional
    public SendMessageResponseSendMessageApi processAndQueueMessage(SendMessageRequestSendMessageApi request, UUID idempotencyKey, String authToken) {
        // Idempotency check
        if (idempotencyKey != null) {
            String requestHash = createRequestHash(request);
            Optional<IdempotencyKeySendMessageApi> existing = idempotencyRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                if (existing.get().getRequestHash().equals(requestHash)) {
                    // Return stored response for duplicate request
                    return deserializeResponse(existing.get().getResponseBody());
                } else {
                    // Different request with same key
                    throw new IdempotencyConflictExceptionSendMessageApi("Idempotency-Key reused with a different request payload.");
                }
            }
        }

        // Perform business logic checks
        String senderId = authService.validateTokenAndGetSenderId(authToken);
        userLookupService.findUserById(request.recipientId());
        authorizationService.checkPermission(senderId, request.recipientId());

        // Persist the message
        MessageSendMessageApi message = createAndSaveMessage(request);
        
        // Queue for delivery
        messageQueue.sendMessage(message);

        // Create response
        SendMessageResponseSendMessageApi response = new SendMessageResponseSendMessageApi(
            message.getId(),
            message.getStatus(),
            message.getCreatedAt()
        );

        // Store idempotency record if key was provided
        if (idempotencyKey != null) {
            saveIdempotencyRecord(idempotencyKey, request, response);
        }

        return response;
    }

    /**
     * Creates and saves a new message entity to the database.
     */
    private MessageSendMessageApi createAndSaveMessage(SendMessageRequestSendMessageApi request) {
        MessageSendMessageApi message = new MessageSendMessageApi();
        message.setRecipientId(UUID.fromString(request.recipientId()));
        message.setContent(request.content());
        message.setStatus(MessageStatusSendMessageApi.QUEUED);
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new ServiceUnavailableExceptionSendMessageApi("Failed to save message to database.", e);
        }
    }
    
    /**
     * Saves the idempotency key, request hash, and response for future checks.
     */
    private void saveIdempotencyRecord(UUID key, SendMessageRequestSendMessageApi request, SendMessageResponseSendMessageApi response) {
        IdempotencyKeySendMessageApi record = new IdempotencyKeySendMessageApi();
        record.setIdempotencyKey(key);
        record.setRequestHash(createRequestHash(request));
        record.setResponseBody(serializeResponse(response));
        record.setResponseStatus(202);
        try {
            idempotencyRepository.save(record);
        } catch (Exception e) {
            // Log this failure but don't fail the request, as the core action succeeded
            // In a real system, a separate process might clean up failed idempotency saves.
        }
    }

    /**
     * Creates a SHA-256 hash of the request object for idempotency checks.
     */
    private String createRequestHash(Object request) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(requestJson.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create request hash", e);
        }
    }
    
    private String serializeResponse(SendMessageResponseSendMessageApi response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize response for idempotency", e);
        }
    }

    private SendMessageResponseSendMessageApi deserializeResponse(String json) {
        try {
            return objectMapper.readValue(json, SendMessageResponseSendMessageApi.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not deserialize stored idempotent response", e);
        }
    }
}
```
```java