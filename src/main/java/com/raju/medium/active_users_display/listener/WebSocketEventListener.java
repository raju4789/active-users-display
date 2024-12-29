package com.raju.medium.active_users_display.listener;

import com.raju.medium.active_users_display.controller.WebSocketController;
import com.raju.medium.active_users_display.service.ActiveUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final ActiveUserService activeUserService;
    private final WebSocketController webSocketController;

    public WebSocketEventListener(ActiveUserService activeUserService, WebSocketController webSocketController) {
        this.activeUserService = activeUserService;
        this.webSocketController = webSocketController;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        try {
            // Extract session ID using a safer approach
            String sessionId = extractSessionId(event);
            if (sessionId != null) {
                activeUserService.addUser(sessionId); // Add session ID to Redis
                webSocketController.broadcastUserCount(activeUserService.getActiveUserCount());
                logger.info("User connected: {}", sessionId);
            } else {
                logger.warn("Failed to extract session ID during connection event.");
            }
        } catch (Exception e) {
            logger.error("Error handling WebSocket connection event", e);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        try {
            // Extract session ID using a safer approach
            String sessionId = extractSessionId(event);
            if (sessionId != null) {
                activeUserService.removeUser(sessionId); // Remove session ID from Redis
                webSocketController.broadcastUserCount(activeUserService.getActiveUserCount());
                logger.info("User disconnected: {}", sessionId);
            } else {
                logger.warn("Failed to extract session ID during disconnection event.");
            }
        } catch (Exception e) {
            logger.error("Error handling WebSocket disconnection event", e);
        }
    }

    private String extractSessionId(Object event) {
        if (event instanceof SessionConnectEvent connectEvent) {
            return connectEvent.getMessage().getHeaders().get("simpSessionId", String.class);
        } else if (event instanceof SessionDisconnectEvent disconnectEvent) {
            return disconnectEvent.getSessionId(); // Directly available in SessionDisconnectEvent
        }
        return null;
    }
}
