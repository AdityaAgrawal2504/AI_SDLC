package com.example.messaging.controller;

import com.example.messaging.dto.SendMessageRequestSendMessageApi;
import com.example.messaging.dto.SendMessageResponseSendMessageApi;
import com.example.messaging.service.MessageServiceSendMessageApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * REST controller for handling message-related API endpoints.
 */
@RestController
@RequestMapping("/api/v1/messages")
@Tag(name = "Message API", description = "Endpoints for sending and managing messages")
public class MessageControllerSendMessageApi {

    private final MessageServiceSendMessageApi messageService;

    public MessageControllerSendMessageApi(MessageServiceSendMessageApi messageService) {
        this.messageService = messageService;
    }

    /**
     * Accepts and queues a message for delivery to a specific recipient.
     */
    @Operation(summary = "Sends a message to a specific recipient.",
        responses = {
            @ApiResponse(responseCode = "202", description = "The message has been accepted and is queued for delivery.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SendMessageResponseSendMessageApi.class))),
            @ApiResponse(responseCode = "400", description = "The request is malformed or failed validation."),
            @ApiResponse(responseCode = "401", description = "Authentication credentials are required and are missing or invalid."),
            @ApiResponse(responseCode = "403", description = "The authenticated user is not permitted to perform this action."),
            @ApiResponse(responseCode = "404", description = "The specified recipient could not be found."),
            @ApiResponse(responseCode = "409", description = "An Idempotency-Key was reused with a different request payload."),
            @ApiResponse(responseCode = "429", description = "The user has exceeded the rate limit."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
        })
    @PostMapping
    public ResponseEntity<SendMessageResponseSendMessageApi> sendMessage(
        @RequestHeader(name = "Authorization") String authorization,
        @RequestHeader(name = "Idempotency-Key", required = false) UUID idempotencyKey,
        @Valid @RequestBody SendMessageRequestSendMessageApi request) {

        SendMessageResponseSendMessageApi response = messageService.processAndQueueMessage(request, idempotencyKey, authorization);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getMessageId())
            .toUri();

        return ResponseEntity.accepted().location(location).body(response);
    }
}
```
```java