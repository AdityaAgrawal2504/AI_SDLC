package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Chat_C1A7;
import com.chatapp.chatservice.domain.Message_C1A7;
import com.chatapp.chatservice.enums.StreamErrorCode_C1A7;
import com.chatapp.chatservice.exception.StreamErrorException_C1A7;
import com.chatapp.chatservice.grpc.*;
import com.chatapp.chatservice.repository.MessageRepository_C1A7;
import com.chatapp.chatservice.util.TimestampConverter_C1A7;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageService_C1A7 {

    private final MessageRepository_C1A7 messageRepository;
    private final ConnectionManager_C1A7 connectionManager;
    private final ChatPermissionService_C1A7 permissionService;

    /**
     * Processes a new incoming message, saves it, and broadcasts it to chat participants.
     */
    public void processNewMessage(SendMessageRequest request, String senderId) {
        Chat_C1A7 chat = permissionService.findAndVerifyChatMembership(request.getChatId(), senderId);

        if (permissionService.isSenderMuted(chat, senderId)) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MESSAGE_SEND_FAILED_MUTED,
                    "Sender is muted in this chat.", request.getClientMessageId());
        }

        Set<String> allRecipientIds = chat.getParticipantIds().stream()
                .filter(id -> !id.equals(senderId))
                .collect(Collectors.toSet());

        Set<String> deliverableRecipientIds = permissionService.filterBlockedRecipients(allRecipientIds, senderId);

        // Save the message
        Message_C1A7 message = Message_C1A7.builder()
                .messageId(UUID.randomUUID().toString())
                .clientMessageId(request.getClientMessageId())
                .chatId(request.getChatId())
                .senderId(senderId)
                .content(request.getContent())
                .status(MessageStatus.SENT)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        messageRepository.save(message);
        log.info("Saved new message {} from sender {}", message.getMessageId(), senderId);

        // Build the event
        NewMessageEvent event = NewMessageEvent.newBuilder()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setSenderId(message.getSenderId())
                .setContent(message.getContent())
                .setCreatedAt(TimestampConverter_C1A7.fromInstant(message.getCreatedAt()))
                .setClientMessageId(message.getClientMessageId())
                .build();
        ServerToClientMessage serverMessage = ServerToClientMessage.newBuilder().setNewMessageEvent(event).build();

        // Broadcast to sender and all deliverable recipients
        Set<String> broadcastList = deliverableRecipientIds;
        broadcastList.add(senderId);
        connectionManager.broadcast(broadcastList, serverMessage);
    }

    /**
     * Processes a message status update, saves it, and broadcasts it to chat participants.
     */
    public void processStatusUpdate(UpdateStatusRequest request, String updaterId) {
        if (request.getStatus() != MessageStatus.SEEN) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.INVALID_STATUS_UPDATE,
                    "Clients can only update status to SEEN.", null);
        }
        
        Chat_C1A7 chat = permissionService.findAndVerifyChatMembership(request.getChatId(), updaterId);

        Message_C1A7 message = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new StreamErrorException_C1A7(StreamErrorCode_C1A7.NOT_FOUND,
                        "Message with ID " + request.getMessageId() + " not found.", null));

        if (message.getSenderId().equals(updaterId)) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.INVALID_STATUS_UPDATE,
                    "Cannot mark your own message as SEEN.", null);
        }

        // Update and save status
        message.setStatus(request.getStatus());
        message.setUpdatedAt(Instant.now());
        messageRepository.save(message);
        log.info("Updated message {} status to {} by user {}", message.getMessageId(), request.getStatus(), updaterId);

        // Build the event
        MessageStatusUpdateEvent event = MessageStatusUpdateEvent.newBuilder()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setStatus(message.getStatus())
                .setUpdatedAt(TimestampConverter_C1A7.fromInstant(message.getUpdatedAt()))
                .setUpdatedByUserId(updaterId)
                .build();
        ServerToClientMessage serverMessage = ServerToClientMessage.newBuilder().setMessageStatusUpdateEvent(event).build();

        // Broadcast to all chat participants
        connectionManager.broadcast(chat.getParticipantIds(), serverMessage);
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/util/RequestValidator_C1A7.java