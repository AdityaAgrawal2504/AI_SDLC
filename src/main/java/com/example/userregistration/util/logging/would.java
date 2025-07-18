package com.example.userregistration.util.logging;

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
import java.nio.charset.StandardCharsets;

/**
 * A custom Log4j2 Appender to demonstrate an abstracted logging layer.
 * In a real-world scenario, this class would contain logic to send logs
 * to an external system like Kafka, an ELK stack, or Azure Event Hub.
 */
@Plugin(name = "ExternalSystemLogAppender", category = "Core", elementType = "appender", printObject = true)
public class ExternalSystemLogAppender extends AbstractAppender {

    protected ExternalSystemLogAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout, true, null);
    }

    /**
     * Factory method for creating the appender instance.
     * @param name The appender name.
     * @param filter The filter to apply.
     * @param layout The layout to use.
     * @return A new instance of ExternalSystemLogAppender.
     */
    @PluginFactory
    public static ExternalSystemLogAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        if (name == null) {
            LOGGER.error("No name provided for ExternalSystemLogAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new ExternalSystemLogAppender(name, filter, layout);
    }

    /**
     * This method is called by Log4j2 to process a log event.
     * This is the entry point for the custom logging logic.
     * @param event The log event to be processed.
     */
    @Override
    public void append(LogEvent event) {
        // In a production implementation, you would format the log event
        // and send it to an external message queue or logging service.
        // For example, using a KafkaProducer client.
        
        final byte[] bytes = getLayout().toByteArray(event);
        String logMessage = new String(bytes, StandardCharsets.UTF_8);

        // This is a placeholder for the actual integration logic.
        System.out.printf("[EXTERNAL_LOG_SIMULATION] Would send to Kafka/EventHub: %s%n", logMessage.trim());
    }
}
```