package com.fetchmessagesapi.fma.util;

import com.fetchmessagesapi.fma.enums.MessageStatusFMA;
import com.fetchmessagesapi.fma.model.MessageEntityFMA;
import com.fetchmessagesapi.fma.repository.MessageRepositoryFMA;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

/**
 * Component to initialize the in-memory H2 database with sample data on application startup.
 */
@Component
@RequiredArgsConstructor
public class DataInitializerFMA implements CommandLineRunner {

    private final MessageRepositoryFMA messageRepository;

    @Override
    public void run(String... args) throws Exception {
        if (messageRepository.count() > 0) {
            return; // Data already exists
        }

        UUID recipientId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Matches mock user
        UUID sender1Id = UUID.randomUUID();
        UUID sender2Id = UUID.randomUUID();
        UUID conversation1 = UUID.randomUUID();
        UUID conversation2 = UUID.randomUUID();

        // Message 1 (unread)
        MessageEntityFMA msg1 = new MessageEntityFMA();
        msg1.setRecipientId(recipientId);
        msg1.setSenderId(sender1Id);
        msg1.setSenderName("Alice");
        msg1.setConversationId(conversation1);
        msg1.setSubject("Project Update");
        msg1.setBody("Here is the latest update on the project. The search functionality is now complete.");
        msg1.setTimestamp(Instant.now().minusSeconds(3600)); // 1 hour ago
        msg1.setStatus(MessageStatusFMA.unread);
        messageRepository.save(msg1);

        // Message 2 (read)
        MessageEntityFMA msg2 = new MessageEntityFMA();
        msg2.setRecipientId(recipientId);
        msg2.setSenderId(sender2Id);
        msg2.setSenderName("Bob");
        msg2.setConversationId(conversation2);
        msg2.setSubject("Lunch Plans");
        msg2.setBody("Are we still on for lunch tomorrow? Let's discuss the status of the new API.");
        msg2.setTimestamp(Instant.now().minusSeconds(7200)); // 2 hours ago
        msg2.setStatus(MessageStatusFMA.read);
        messageRepository.save(msg2);

        // Message 3 (unread, same conversation as msg1)
        MessageEntityFMA msg3 = new MessageEntityFMA();
        msg3.setRecipientId(recipientId);
        msg3.setSenderId(sender1Id);
        msg3.setSenderName("Alice");
        msg3.setConversationId(conversation1);
        msg3.setSubject("Re: Project Update");
        msg3.setBody("I've attached the document. Please review the search criteria section.");
        msg3.setTimestamp(Instant.now().minusSeconds(1800)); // 30 minutes ago
        msg3.setStatus(MessageStatusFMA.unread);
        messageRepository.save(msg3);
        
        // Message 4 (read, older)
        MessageEntityFMA msg4 = new MessageEntityFMA();
        msg4.setRecipientId(recipientId);
        msg4.setSenderId(sender2Id);
        msg4.setSenderName("Bob");
        msg4.setConversationId(UUID.randomUUID());
        msg4.setSubject("Important Notice");
        msg4.setBody("Please be aware of the new security policy updates.");
        msg4.setTimestamp(Instant.now().minusSeconds(86400)); // 1 day ago
        msg4.setStatus(MessageStatusFMA.read);
        messageRepository.save(msg4);
    }
}
```