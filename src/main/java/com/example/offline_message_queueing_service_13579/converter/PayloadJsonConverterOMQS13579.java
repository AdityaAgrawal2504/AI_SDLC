package com.example.offline_message_queueing_service_13579.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
@Converter(autoApply = true)
@RequiredArgsConstructor
@Log4j2
public class PayloadJsonConverterOMQS13579 implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize payload to JSON string.", e);
            throw new IllegalArgumentException("Error converting payload to JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Failed to deserialize JSON string to payload.", e);
            throw new IllegalArgumentException("Error converting JSON to payload", e);
        }
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/entity/QueuedMessageEntityOMQS13579.java