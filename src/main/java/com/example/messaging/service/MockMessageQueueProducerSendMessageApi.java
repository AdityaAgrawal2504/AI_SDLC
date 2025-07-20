package com.example.messaging.service;

import com.example.messaging.entity.MessageSendMessageApi;
import com.example.messaging.util.StructuredLoggerSendMessageApi;
import org.springframework.stereotype.Service;

/**
 * A mock implementation of a message queue producer that logs to the console.
 */
@Service
public class MockMessageQueueProducerSendMessageApi implements MessageQueueProducerSendMessageApi {
    private static final StructuredLoggerSendMessageApi logger = StructuredLoggerSendMessageApi.getLogger(MockMessageQueueProducerSendMessageApi.class);

    /**
     * Simulates sending a message to a queue by logging its details.
     */
    @Override
    public void sendMessage(MessageSendMessageApi message) {
        logger.logInfo("Message queued for delivery",
            "messageId", message.getId(),
            "recipientId", message.getRecipientId());
        // In a real implementation, this would connect to RabbitMQ, Kafka, SQS, etc.
        // For example: rabbitTemplate.convertAndSend("message-exchange", "message.route", message);
    }
}
```
```java