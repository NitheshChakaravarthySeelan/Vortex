package com.chat.vortex.gateway.dispatcher;

import com.chat.vortex.shared.model.Packet;
import com.chat.vortex.gateway.handler.PacketHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.List;

/**
 * Routes incoming Packets to the appropriate PacketHandler based on the OperationType.
 * It dynamically registers all available handlers in the Spring context.
 */
@Component
public class PacketDispatcher {
    
    private final List<PacketHandler> handlers;

    /**
     * Constructs a new PacketDispatcher with a list of available packet handlers.
     * 
     * @param handlers The list of injected handlers that implement PacketHandler.
     */
    public PacketDispatcher(List<PacketHandler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Iterates through the available handlers and delegates the packet to the first
     * one that supports the packet's operation type.
     * 
     * @param packet The packet containing operation type and data payload.
     * @param session The WebSocket session of the sender.
     */
    public void dispatch(Packet packet, WebSocketSession session) {
        for (PacketHandler handler : handlers) {
            if (handler.supports(packet.operation())) {
                handler.handle(session, packet.data());
                return;
            }
        }
        System.err.println("No handler found for operation: " + packet.operation());
    }
}