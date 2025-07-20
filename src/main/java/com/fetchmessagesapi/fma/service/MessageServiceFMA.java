package com.fetchmessagesapi.fma.service;

import com.fetchmessagesapi.fma.dto.MessageFMA;
import com.fetchmessagesapi.fma.dto.MessageListResponseFMA;
import com.fetchmessagesapi.fma.dto.PaginationInfoFMA;
import com.fetchmessagesapi.fma.dto.SenderFMA;
import com.fetchmessagesapi.fma.enums.ApiErrorFMA;
import com.fetchmessagesapi.fma.enums.SortOrderFMA;
import com.fetchmessagesapi.fma.exception.ApiExceptionFMA;
import com.fetchmessagesapi.fma.model.MessageEntityFMA;
import com.fetchmessagesapi.fma.repository.MessageRepositoryFMA;
import com.fetchmessagesapi.fma.repository.MessageSpecificationFMA;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the business logic for fetching user messages.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceFMA implements IMessageServiceFMA {

    private static final Logger log = LogManager.getLogger(MessageServiceFMA.class);
    private final MessageRepositoryFMA messageRepository;

    /**
     * Retrieves messages from the data store based on the provided options,
     * builds a paginated response, and maps entities to DTOs.
     *
     * <pre>
     * {@code
     * mermaid
     * flowchart TD
     *     A[Start: fetchMessages] --> B{Build JPA Specification};
     *     B --> C{Build Pageable with Sort};
     *     C --> D[Execute Repository Query];
     *     D --> E{Check for Errors};
     *     E -- No Errors --> F[Map Entities to DTOs];
     *     F --> G[Build PaginationInfo DTO];
     *     G --> H[Construct MessageListResponse];
     *     H --> I[End: Return Response];
     *     E -- DB Error --> J[Throw ApiException];
     *     J --> I;
     * }
     * </pre>
     *
     * @param options The consolidated fetching criteria.
     * @return A MessageListResponseFMA containing the data and pagination info.
     */
    @Override
    @Transactional(readOnly = true)
    public MessageListResponseFMA fetchMessages(IMessageFetchOptionsFMA options) {
        try {
            Pageable pageable = createPageable(options);
            Specification<MessageEntityFMA> spec = MessageSpecificationFMA.from(options);

            Page<MessageEntityFMA> messagePage = messageRepository.findAll(spec, pageable);

            List<MessageFMA> messageDTOs = messagePage.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

            PaginationInfoFMA paginationInfo = PaginationInfoFMA.builder()
                .totalItems(messagePage.getTotalElements())
                .limit(options.getLimit())
                .offset(options.getOffset())
                .currentItemCount(messagePage.getNumberOfElements())
                .build();

            return new MessageListResponseFMA(messageDTOs, paginationInfo);
        } catch (Exception ex) {
            log.error("Failed to fetch messages for user {}", options.getAuthenticatedUserId(), ex);
            throw new ApiExceptionFMA(ApiErrorFMA.MESSAGE_FETCH_FAILED);
        }
    }

    /**
     * Creates a Pageable object for pagination and sorting.
     * @param options The fetching criteria.
     * @return A PageRequest object.
     */
    private Pageable createPageable(IMessageFetchOptionsFMA options) {
        Sort.Direction direction = options.getSortOrder() == SortOrderFMA.asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, options.getSortBy().getDbField());
        int pageNumber = options.getOffset() / options.getLimit();
        return PageRequest.of(pageNumber, options.getLimit(), sort);
    }

    /**
     * Maps a MessageEntityFMA to its corresponding MessageFMA DTO.
     * @param entity The source JPA entity.
     * @return The resulting DTO.
     */
    private MessageFMA mapEntityToDto(MessageEntityFMA entity) {
        return MessageFMA.builder()
            .id(entity.getId())
            .conversationId(entity.getConversationId())
            .sender(new SenderFMA(entity.getSenderId(), entity.getSenderName()))
            .subject(entity.getSubject())
            .bodySnippet(truncateBody(entity.getBody()))
            .timestamp(entity.getTimestamp())
            .status(entity.getStatus())
            .build();
    }

    /**
     * Creates a short preview snippet from the message body.
     * @param body The full message body.
     * @return A truncated string (e.g., first 100 characters).
     */
    private String truncateBody(String body) {
        if (body == null || body.length() <= 100) {
            return body;
        }
        return body.substring(0, 100) + "...";
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/util/DataInitializerFMA.java