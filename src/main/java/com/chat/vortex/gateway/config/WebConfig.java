package com.chat.vortex.gateway.config;

import com.chat.vortex.gateway.handler.MyWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

/**
 * Configuration class for the WebSocket gateway.
 * Maps the WebSocket endpoints and provides necessary beans like ObjectMapper.
 */
@Configuration
public class WebConfig {

    /**
     * Maps the /ws endpoint to our custom WebSocket handler.
     * 
     * @param handler The WebSocket handler for incoming connections.
     * @return A SimpleUrlHandlerMapping bean.
     */
    @Bean
    public HandlerMapping handlerMapping(MyWebSocketHandler handler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws", handler);
        int order = -1;

        return new SimpleUrlHandlerMapping(map, order);
    }

    /**
     * Provides the WebSocketHandlerAdapter bean required by Spring WebFlux to
     * support WebSocket handlers.
     * 
     * @return A WebSocketHandlerAdapter bean.
     */
    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    /**
     * Provides the ObjectMapper bean for JSON serialization and deserialization.
     * 
     * @return An ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
