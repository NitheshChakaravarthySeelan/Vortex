package com.chat.vortex.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.chat.vortex.kafka.event.MessageCreatedEvent;

@Service
public class MessageConsumer {
    @KafkaListener(
        topics = "messages",
        groupId = "gateway"
    )
    public void consume(MessageCreatedEvent event) {
        // We need to think of some method to braodcast to user
        System.out.println(event);
    }
}
