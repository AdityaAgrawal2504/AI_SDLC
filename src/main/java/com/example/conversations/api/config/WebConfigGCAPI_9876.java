package com.example.conversations.api.config;

import com.example.conversations.api.util.StringToConversationSortByConverterGCAPI_9876;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures web layer aspects, such as custom converters for request parameters.
 */
@Configuration
public class WebConfigGCAPI_9876 implements WebMvcConfigurer {

    /**
     * Adds custom converters to handle specific string-to-enum conversions for API parameters.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToConversationSortByConverterGCAPI_9876());
    }
}
```
```java
// src/main/java/com/example/conversations/api/controller/ConversationControllerGCAPI_9876.java