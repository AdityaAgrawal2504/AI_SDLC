package com.example.chat.logging;

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

/**
 * A custom Log4j2 Appender to abstract logging to external systems like Kafka or Event Hub.
 * This is a placeholder implementation to demonstrate the concept.
 */
@Plugin(name = "ExternalLogSystemGRPC", category = "Core", elementType = "appender", printObject = true)
public class ExternalLogSystemAppenderGRPC extends AbstractAppender {

    private final ExternalLogSystemManagerGRPC manager;

    protected ExternalLogSystemAppenderGRPC(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, ExternalLogSystemManagerGRPC manager) {
        super(name, filter, layout, ignoreExceptions, null);
        this.manager = manager;
    }

    @PluginFactory
    public static ExternalLogSystemAppenderGRPC createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) boolean ignoreExceptions,
            @PluginAttribute("topic") String topic) {

        if (name == null) {
            LOGGER.error("No name provided for ExternalLogSystemAppenderGRPC");
            return null;
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        final ExternalLogSystemManagerGRPC manager = ExternalLogSystemManagerGRPC.getManager("ExternalLogSystemManager-" + name, topic);

        return new ExternalLogSystemAppenderGRPC(name, filter, layout, ignoreExceptions, manager);
    }

    @Override
    public void append(LogEvent event) {
        final byte[] bytes = getLayout().toByteArray(event);
        // In a real implementation, this would send to Kafka, Event Hub, etc.
        manager.write(bytes);
    }
}
```
src/main/java/com/example/chat/logging/ExternalLogSystemManagerGRPC.java
```java