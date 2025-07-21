package com.chatapp.fmh_8721.util;

import io.github.thibaultmeyer.cuid.Cuid;
import org.springframework.stereotype.Component;

/**
 * Utility component for generating CUIDs for message IDs.
 */
@Component
public class CuidGeneratorFMH_8721 {

    private static final String MESSAGE_PREFIX = "msg_";

    /**
     * Generates a new, unique CUID prefixed for messages.
     * @return A prefixed CUID string.
     */
    public String newId() {
        return MESSAGE_PREFIX + Cuid.create();
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/config/ValidationConfigFMH_8721.java