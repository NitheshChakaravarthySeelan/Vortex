package com.chat.vortex.gateway.session;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

/**
 * Registry for managing WebSocket sessions and their associations with users and channels.
 * This class keeps track of who is connected, what channels they are in, and helps facilitate
 * message broadcasting.
 */
@Component
public class SessionRegistry {

    /**
     * Maps a WebSocket session ID to the corresponding User ID.
     * Used to identify which user owns a particular WebSocket connection.
     */
    public final Map<String, UUID> sessionToUserMap = new ConcurrentHashMap<>();

    /**
     * Maps a Channel ID to a set of active WebSocket sessions.
     * Used for broadcasting messages to all users currently in a specific channel.
     */
    public final Map<UUID, Set<WebSocketSession>> channelToUserSessionsMap = new ConcurrentHashMap<>();

    /**
     * Maps a WebSocket session ID to a set of Channel IDs they are subscribed to.
     * Used to quickly clean up channel subscriptions when a WebSocket disconnects.
     */
    public final Map<String, Set<UUID>> sessionToChannelsMap = new ConcurrentHashMap<>();

    /**
     * Registers a new WebSocket session and associates it with a user.
     * Called when a user successfully connects and initializes their WebSocket.
     *
     * @param sessionId The unique ID of the WebSocket session.
     * @param userId The unique ID of the authenticated user.
     */
    public void addSession(String sessionId, UUID userId) {
        sessionToUserMap.put(sessionId, userId);
    }

    /**
     * Subscribes a WebSocket session to a specific chat channel.
     * Enables the session to receive broadcast messages sent to this channel.
     *
     * @param channelId The unique ID of the channel to join.
     * @param session The WebSocket session joining the channel.
     */
    public void addToChannel(UUID channelId, WebSocketSession session) {
        channelToUserSessionsMap.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    /**
     * Retrieves all active WebSocket sessions currently subscribed to a channel.
     * Used primarily for broadcasting messages to all participants in the channel.
     *
     * @param channelId The unique ID of the channel.
     * @return A set of active WebSocket sessions in the channel, or an empty set if none exist.
     */
    public Set<WebSocketSession> getChannelUsers(UUID channelId) {
        return channelToUserSessionsMap.getOrDefault(channelId, ConcurrentHashMap.newKeySet());
    }

    /**
     * Unsubscribes a WebSocket session from a specific chat channel.
     *
     * @param channelId The unique ID of the channel to leave.
     * @param session The WebSocket session leaving the channel.
     */
    public void removeFromChannel(UUID channelId, WebSocketSession session) {
        channelToUserSessionsMap.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet()).remove(session);
    }

    /**
     * Completely removes a session from the registry.
     * Called when a user logs out or their connection is permanently terminated.
     *
     * @param sessionId The unique ID of the WebSocket session to remove.
     */
    public void removeSession(String sessionId) {
        sessionToUserMap.remove(sessionId);
        sessionToChannelsMap.remove(sessionId);
    }

    /**
     * Retrieves the User ID associated with a given WebSocket session ID.
     *
     * @param sessionId The unique ID of the WebSocket session.
     * @return An Optional containing the User ID if found, otherwise empty.
     */
    public Optional<UUID> getUserSession(String sessionId) {
        return Optional.ofNullable(sessionToUserMap.get(sessionId));
    }

    /**
     * Retrieves all Channel IDs that a specific WebSocket session is subscribed to.
     *
     * @param sessionId The unique ID of the WebSocket session.
     * @return A set of Channel IDs the session is subscribed to.
     */
    public Set<UUID> getSessionChannels(String sessionId) {
        return sessionToChannelsMap.getOrDefault(sessionId, ConcurrentHashMap.newKeySet());
    }

    /**
     * Removes all channel subscriptions for a specific session.
     * Called when a WebSocket is closed to prevent sending messages to a dead session.
     *
     * @param sessionId The unique ID of the WebSocket session.
     */
    public void removeSessionFromChannels(String sessionId) {
        sessionToChannelsMap.remove(sessionId);
    }
}