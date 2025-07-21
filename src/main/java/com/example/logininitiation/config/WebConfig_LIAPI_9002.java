package com.example.logininitiation.config;

import com.example.logininitiation.logging.MDCFilter_LIAPI_9502;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig_LIAPI_9002 {

    /**
     * Registers the MDCFilter to process all incoming requests.
     * This ensures the X-Request-ID is captured and added to the logging context.
     */
    @Bean
    public FilterRegistrationBean<MDCFilter_LIAPI_9502> mdcFilterRegistration() {
        FilterRegistrationBean<MDCFilter_LIAPI_9502> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MDCFilter_LIAPI_9502());
        registration.addUrlPatterns("/api/*");
        registration.setName("mdcFilter");
        registration.setOrder(1);
        return registration;
    }
}
```
```java
// src/main/java/com/example/logininitiation/controller/LoginInitiationController_LIAPI_8001.java