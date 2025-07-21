package com.chatapp.fmh_8721.util;

import com.chatapp.fmh_8721.domain.*;
import com.chatapp.fmh_8721.repository.ConversationRepositoryFMH_8721;
import com.chatapp.fmh_8721.repository.MessageRepositoryFMH_8721;
import com.chatapp.fmh_8721.repository.UserRepositoryFMH_8721;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Initializes the database with sample data for demonstration purposes.
 */
@Component
@RequiredArgsConstructor
public class DataInitializerFMH_8721 implements CommandLineRunner {

    private final UserRepositoryFMH_8721 userRepository;
    private final ConversationRepositoryFMH_8721 conversationRepository;
    private final MessageRepositoryFMH_8721 messageRepository;
    private final CuidGeneratorFMH_8721 cuidGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create users
        UserEntityFMH_8721 user1 = createUser(
                "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                "Jane Doe",
                "https://example.com/avatars/jane_doe.png",
                "password"
        );
        UserEntityFMH_8721 user2 = createUser(
                "b2c3d4e5-f6a7-8901-2345-67890abcdef1",
                "Richard Roe",
                "https://example.com/avatars/richard_roe.png",
                "password"
        );
        createUser(
                "00000000-0000-0000-0000-000000000000",
                "John Smith",
                "https://example.com/avatars/john_smith.png",
                "password"
        );

        // Create a conversation
        ConversationEntityFMH_8721 conversation = new ConversationEntityFMH_8721();
        conversation.setId(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        conversation.setParticipants(Set.of(user1, user2));
        conversationRepository.save(conversation);

        // Create messages for the conversation
        List<MessageEntityFMH_8721> messages = new ArrayList<>();
        Instant now = Instant.now();
        for (int i = 0; i < 50; i++) {
            MessageEntityFMH_8721 message = new MessageEntityFMH_8721();
            message.setId(cuidGenerator.newId());
            message.setConversationId(conversation.getId());
            message.setAuthor((i % 2 == 0) ? user1 : user2);
            message.setContentType(MessageContentTypeFMH_8721.TEXT);
            message.setTextContent("This is message number " + (50 - i) + " in our sample conversation.");
            message.setStatus(MessageStatusFMH_8721.READ);
            // Set createdAt in reverse chronological order
            message.setCreatedAt(now.minus(i, ChronoUnit.MINUTES));
            messages.add(message);
        }
        messageRepository.saveAll(messages);
    }
    
    private UserEntityFMH_8721 createUser(String uuid, String name, String avatar, String rawPassword) {
        UserEntityFMH_8721 user = new UserEntityFMH_8721();
        user.setId(UUID.fromString(uuid));
        user.setDisplayName(name);
        user.setAvatarUrl(avatar);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }
}
```