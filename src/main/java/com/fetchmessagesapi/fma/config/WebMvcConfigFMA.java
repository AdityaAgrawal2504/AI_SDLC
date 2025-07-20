package com.fetchmessagesapi.fma.config;

import com.fetchmessagesapi.fma.controller.resolver.UserIdArgumentResolverFMA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configures web-related beans, such as custom argument resolvers.
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfigFMA implements WebMvcConfigurer {

    private final UserIdArgumentResolverFMA userIdArgumentResolver;

    /**
     * Adds the custom argument resolver to the Spring MVC configuration.
     * This allows injecting the authenticated user's ID directly into controller methods.
     * @param resolvers The list of currently configured resolvers.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdArgumentResolver);
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/controller/MessageControllerFMA.java