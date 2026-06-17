package com.chat.vortex.kafka.consumer;

import com.chat.vortex.kafka.event.Event;

/**
 * A generic interface for defining Kafka event consumers.
 * Implementations of this interface will handle specific types of events
 * received from message broker topics.
 */
public interface Consumer {
    
    /**
     * Processes an incoming event from the message broker.
     * Implementations should inspect the event and perform the necessary business logic
     * (e.g., broadcasting to WebSockets, saving to the database).
     *
     * @param event The generalized event payload received.
     */
    void consume(Event event);
    
}