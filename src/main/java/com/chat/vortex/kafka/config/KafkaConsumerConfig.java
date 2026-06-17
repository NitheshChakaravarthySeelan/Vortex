package com.chat.vortex.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import com.chat.vortex.kafka.event.Event;
import com.chat.vortex.kafka.event.MessageCreatedEvent;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Event> consumerFactory() {
        // We need to initlized with the object cause we don't know in what format the byte data over the internet should be
        JacksonJsonDeserializer<Event> deserializer = new JacksonJsonDeserializer<>(Event.class);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        config.put(ConsumerConfig.GROUP_ID_CONFIG, "messages");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }   
}