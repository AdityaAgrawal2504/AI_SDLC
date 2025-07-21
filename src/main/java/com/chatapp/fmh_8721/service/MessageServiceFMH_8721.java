package com.chatapp.fmh_8721.service;

import com.chatapp.fmh_8721.domain.MessageEntityFMH_8721;
import com.chatapp.fmh_8721.dto.FetchMessagesResponseFMH_8721;
import com.chatapp.fmh_8721.dto.MessageFMH_8721;
import com.chatapp.fmh_8721.dto.PaginationInfoFMH_8721;
import com.chatapp.fmh_8721.exception.ForbiddenExceptionFMH_8721;
import com.chatapp.fmh_8721.exception.InvalidCursorExceptionFMH_8721;
import com.chatapp.fmh_8721.exception.ResourceNotFoundExceptionFMH_8721;
import com.chatapp.fmh_8721.logging.StructuredLoggerFMH_8721;
import com.chatapp.fmh_8721.mapper.MessageMapperFMH_8721;
import com.chatapp.fmh_8721.repository.ConversationRepositoryFMH_8721;
import com.chatapp.fmh_8721.repository.MessageRepositoryFMH_8721;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service containing the business logic for fetching message history.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceFMH_8721 {

    private final MessageRepositoryFMH_8721 messageRepository;
    private final ConversationRepositoryFMH_8721 conversationRepository;
    private final MessageMapperFMH_8721 messageMapper;
    private final SecurityServiceFMH_8721 securityService;
    private final StructuredLoggerFMH_8721 logger;

    /**
     * Fetches a paginated history of messages for a given conversation.
     *
     * @param conversationId The ID of the conversation.
     * @param limit The maximum number of messages to return.
     * @param beforeCursor An optional cursor (message ID) to fetch messages before it.
     * @return A response object containing the messages and pagination info.
     */
    @Transactional(readOnly = true)
    public FetchMessagesResponseFMH_8721 fetchMessageHistory(UUID conversationId, int limit, String beforeCursor) {
        String operationName = "fetchMessageHistory";
        Map<String, Object> logContext = Map.of(
            "conversationId", conversationId,
            "limit", limit,
            "beforeCursor", String.valueOf(beforeCursor)
        );
        long startTime = logger.logStart(operationName, logContext);

        // 1. Authorization: Check if user can access the conversation
        UUID currentUserId = securityService.getCurrentUserId();
        authorizeUserForConversation(conversationId, currentUserId);

        // 2. Fetch one extra item to determine if there are more pages
        int queryLimit = limit + 1;
        List<MessageEntityFMH_8721> messages;

        // 3. Pagination Logic: Fetch based on whether a cursor is present
        if (beforeCursor == null) {
            messages = messageRepository.findByConversationIdOrderByCreatedAtDesc(conversationId, PageRequest.of(0, queryLimit));
        } else {
            Instant cursorTimestamp = getCursorTimestamp(beforeCursor);
            messages = messageRepository.findByConversationIdAndCreatedAtBeforeOrderByCreatedAtDesc(
                conversationId, cursorTimestamp, PageRequest.of(0, queryLimit)
            );
        }

        // 4. Determine pagination info
        boolean hasMore = messages.size() > limit;
        String nextCursor = null;
        if (hasMore) {
            // Remove the extra item, it's only for the 'hasMore' check
            messages.remove(limit);
            // The next cursor is the ID of the last message in the current page
            if (!messages.isEmpty()) {
                nextCursor = messages.get(messages.size() - 1).getId();
            }
        }

        // 5. Map to DTOs
        List<MessageFMH_8721> messageDtos = messages.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
        
        var response = new FetchMessagesResponseFMH_8721(messageDtos, new PaginationInfoFMH_8721(nextCursor, hasMore));
        
        logger.logEnd(operationName, startTime, Map.of(
            "conversationId", conversationId, 
            "messagesFetched", response.data().size(),
            "hasMore", response.pagination().hasMore()
            ));

        return response;
    }
    
    /**
     * Checks if the conversation exists and if the user is a participant.
     */
    private void authorizeUserForConversation(UUID conversationId, UUID userId) {
        if (!conversationRepository.isUserParticipant(conversationId, userId)) {
            // To prevent leaking information, check for existence *after* the participant check fails.
            // If the user is not a participant, the error is either 404 (if convo doesn't exist) or 403.
            if (!conversationRepository.existsById(conversationId)) {
                throw new ResourceNotFoundExceptionFMH_8721("The requested conversation does not exist.");
            }
            throw new ForbiddenExceptionFMH_8721("You do not have permission to access this conversation.");
        }
    }

    /**
     * Retrieves the creation timestamp of the message used as a cursor.
     */
    private Instant getCursorTimestamp(String cursor) {
        return messageRepository.findById(cursor)
                .map(MessageEntityFMH_8721::getCreatedAt)
                .orElseThrow(() -> new InvalidCursorExceptionFMH_8721("The provided 'before' cursor is invalid or does not exist."));
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/controller/ConversationControllerFMH_8721.java