package com.example.conversations.api.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for encoding and decoding pagination cursors.
 */
@Component
@Log4j2
public class CursorUtilGCAPI_9876 {

    private static final String PAIR_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    /**
     * Encodes a map of cursor data into a Base64 string.
     * @param data The map containing cursor data (e.g., id, sortValue).
     * @return A Base64 encoded cursor string.
     */
    public String encode(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String dataString = data.entrySet().stream()
            .map(entry -> entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue())
            .collect(Collectors.joining(PAIR_SEPARATOR));
        return Base64.getUrlEncoder().encodeToString(dataString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64 cursor string back into a map.
     * @param cursor The Base64 encoded cursor string.
     * @return A map of the decoded cursor data, or an empty map if the cursor is invalid.
     */
    public Map<String, String> decode(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return Collections.emptyMap();
        }
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(cursor);
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            
            return Stream.of(decodedString.split(PAIR_SEPARATOR))
                .map(pair -> pair.split(KEY_VALUE_SEPARATOR, 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to decode invalid Base64 cursor: {}", cursor, e);
            return Collections.emptyMap(); // Return empty for invalid cursor
        }
    }
}
```
```java
// src/main/java/com/example/conversations/api/util/SecurityUtilGCAPI_9876.java