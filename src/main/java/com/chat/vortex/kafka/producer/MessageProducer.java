package com.chat.vortex.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.chat.vortex.kafka.event.Event;
import com.chat.vortex.kafka.event.MessageCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProducer implements Producer {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void publish(Event event) {
        if (event instanceof MessageCreatedEvent messageCreatedEvent) {
            kafkaTemplate.send(
                "messages",
                messageCreatedEvent.getChannelId().toString(),
                messageCreatedEvent
            );
        }
        else {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getName());
        }
    }
}