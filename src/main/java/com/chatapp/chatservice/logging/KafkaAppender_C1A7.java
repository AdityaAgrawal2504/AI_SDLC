package com.chatapp.chatservice.logging;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import java.io.Serializable;

@Plugin(name = "KafkaAppender_C1A7", category = "Core", elementType = "appender", printObject = true)
public class KafkaAppender_C1A7 extends AbstractAppender {

    protected KafkaAppender_C1A7(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions, null);
    }

    @PluginFactory
    public static KafkaAppender_C1A7 createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {
        if (name == null) {
            LOGGER.error("No name provided for KafkaAppender_C1A7");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new KafkaAppender_C1A7(name, filter, layout, true);
    }

    /**
     * This is a stub. In a real implementation, this would send the log event to a Kafka topic.
     */
    @Override
    public void append(LogEvent event) {
        // In a real implementation, you would use a Kafka producer to send the message.
        // For example: kafkaProducer.send(new ProducerRecord<>("log-topic", getLayout().toByteArray(event)));
        // System.out.println("LOG (Kafka): " + new String(getLayout().toByteArray(event)));
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/logging/LogDestinationSelector_C1A7.java