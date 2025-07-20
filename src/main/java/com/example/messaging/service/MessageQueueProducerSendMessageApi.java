package com.example.messaging.service;

import com.example.messaging.entity.MessageSendMessageApi;

/**
 * Interface for a message queue producer, abstracting the messaging system.
 */
public interface MessageQueueProducerSendMessageApi {
    void sendMessage(MessageSendMessageApi message);
}
```
```java