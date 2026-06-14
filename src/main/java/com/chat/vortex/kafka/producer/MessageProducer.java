package com.chat.vortex.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.chat.vortex.kafka.event.MessageCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, MessageCreatedEvent> kafkaTemplate;

    public void publish(MessageCreatedEvent event) {
        kafkaTemplate.send(
            "messages",
            event.getChannelId().toString(),
            event
        );
    }
}