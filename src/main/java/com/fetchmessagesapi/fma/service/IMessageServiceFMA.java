package com.fetchmessagesapi.fma.service;

import com.fetchmessagesapi.fma.dto.MessageListResponseFMA;

/**
 * Defines the business logic for handling message-related operations.
 */
public interface IMessageServiceFMA {
    /**
     * Retrieves messages based on specified criteria.
     * @param options The consolidated and validated options for fetching messages.
     * @return A MessageListResponseFMA containing the data and pagination info.
     */
    MessageListResponseFMA fetchMessages(IMessageFetchOptionsFMA options);
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/service/MessageServiceFMA.java