package com.chatapp.fmh_8721.mapper;

import com.chatapp.fmh_8721.domain.MessageEntityFMH_8721;
import com.chatapp.fmh_8721.domain.UserEntityFMH_8721;
import com.chatapp.fmh_8721.dto.MessageContentFMH_8721;
import com.chatapp.fmh_8721.dto.MessageFMH_8721;
import com.chatapp.fmh_8721.dto.UserSummaryFMH_8721;
import org.springframework.stereotype.Component;

/**
 * Maps between MessageEntity and MessageDTO.
 */
@Component
public class MessageMapperFMH_8721 {
    
    /**
     * Converts a MessageEntity to a Message DTO.
     * @param entity The source MessageEntity.
     * @return The resulting Message DTO.
     */
    public MessageFMH_8721 toDto(MessageEntityFMH_8721 entity) {
        if (entity == null) {
            return null;
        }

        return new MessageFMH_8721(
            entity.getId(),
            entity.getConversationId(),
            toUserSummary(entity.getAuthor()),
            toMessageContent(entity),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    /**
     * Converts a UserEntity to a UserSummary DTO.
     * @param author The source UserEntity.
     * @return The resulting UserSummary DTO.
     */
    private UserSummaryFMH_8721 toUserSummary(UserEntityFMH_8721 author) {
        if (author == null) {
            return null;
        }
        return new UserSummaryFMH_8721(
            author.getId(),
            author.getDisplayName(),
            author.getAvatarUrl()
        );
    }
    
    /**
     * Creates a MessageContent DTO from a MessageEntity.
     * @param entity The source MessageEntity.
     * @return The resulting MessageContent DTO.
     */
    private MessageContentFMH_8721 toMessageContent(MessageEntityFMH_8721 entity) {
        return new MessageContentFMH_8721(
            entity.getContentType(),
            entity.getTextContent(),
            entity.getFileUrl(),
            entity.getFileName()
        );
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/util/CuidGeneratorFMH_8721.java