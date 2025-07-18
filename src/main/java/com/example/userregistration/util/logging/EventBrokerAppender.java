package com.example.userregistration.util.logging;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 * A custom Log4j2 Appender that forwards log events to our abstracted ILoggingEventBroker.
 * It's a bridge between the logging framework and our event streaming abstraction.
 */
@Plugin(name = "EventBroker", category = "Core", elementType = "appender", printObject = true)
public class EventBrokerAppender extends AbstractAppender {
    
    // A simple singleton holder to get the broker instance.
    // In a full Spring app, this would be managed by the Spring context.
    private static class BrokerHolder {
        static final ILoggingEventBroker BROKER_INSTANCE = new StubLoggingEventBroker();
    }
    
    protected EventBrokerAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
        BrokerHolder.BROKER_INSTANCE.initialize();
    }

    @PluginFactory
    public static EventBrokerAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {

        if (name == null) {
            LOGGER.error("No name provided for EventBrokerAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new EventBrokerAppender(name, filter, layout, true, null);
    }
    
    @Override
    public void append(LogEvent event) {
        final byte[] bytes = getLayout().toByteArray(event);
        String logMessage = new String(bytes);
        BrokerHolder.BROKER_INSTANCE.send(logMessage);
    }

    @Override
    public void stop() {
        super.stop();
        BrokerHolder.BROKER_INSTANCE.close();
    }
}
```