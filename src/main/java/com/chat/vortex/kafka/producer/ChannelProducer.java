package com.chat.vortex.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.chat.vortex.kafka.event.Event;
import com.chat.vortex.kafka.event.JoinChannelEvent;

import lombok.RequiredArgsConstructor;

/**
 * Kafka producer for channel-related events.
 * Publishes events such as a user joining a channel to the appropriate Kafka topics.
 */
@Service
@RequiredArgsConstructor
public class ChannelProducer implements Producer {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    /**
     * Publishes a channel event to the message broker.
     * Currently supports broadcasting {@link JoinChannelEvent} to the "channels" topic.
     * 
     * @param event The generic event to publish. Must be an instance of a supported channel event type.
     * @throws IllegalArgumentException if the event type is unsupported.
     */
    @Override
    public void publish(Event event) {
        if (event instanceof JoinChannelEvent joinChannelEvent) {
            kafkaTemplate.send(
                "channels",
                joinChannelEvent.getChannelId().toString(),
                joinChannelEvent
            );
        }
        else {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getName());
        }
    }
}
