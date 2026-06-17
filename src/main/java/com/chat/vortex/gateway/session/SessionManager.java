package com.chat.vortex.gateway.session;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

/**
 * High-level service for managing user connections and disconnections.
 * Interacts with the SessionRegistry to keep track of who is online.
 */
@Service
public class SessionManager {
    
    public final SessionRegistry sessionRegistry;

    /**
     * Constructs a new SessionManager.
     * 
     * @param sessionRegistry The underlying data structure storing session mappings.
     */
    public SessionManager(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    /**
     * Registers a newly connected user's WebSocket session.
     * 
     * @param userId The UUID of the user.
     * @param session The active WebSocket session.
     */
    public void connect(UUID userId, WebSocketSession session) {
        sessionRegistry.addSession(session.getId(), userId);
    }

    /**
     * Unregisters a user when their WebSocket session is closed.
     * 
     * @param session The closed WebSocket session.
     */
    public void disconnect(WebSocketSession session) {
        sessionRegistry.removeSession(session.getId());
    }

    /**
     * Checks if a user currently has an active session.
     * 
     * @param userId The UUID of the user to check.
     * @return true if the user is currently online, false otherwise.
     */
    public boolean isOnline(UUID userId) {
        return sessionRegistry.sessionToUserMap.containsValue(userId);
    }
}