package com.raju.medium.active_users_display.listener;

import com.raju.medium.active_users_display.controller.WebSocketController;
import com.raju.medium.active_users_display.service.ActiveUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class WebSocketEventListener {

    private final ActiveUserService activeUserService;
    private final WebSocketController webSocketController;

    public WebSocketEventListener(ActiveUserService activeUserService, WebSocketController webSocketController) {
        this.activeUserService = activeUserService;
        this.webSocketController = webSocketController;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        try {
            String sessionId = extractSessionId(event);
            if (sessionId != null) {
                long newCount = activeUserService.addUserAndGetCount(sessionId);
                webSocketController.broadcastUserCount((int) newCount);
                log.info("User connected: {}. New count: {}", sessionId, newCount);
            } else {
                log.warn("Failed to extract session ID during connection event.");
            }
        } catch (Exception e) {
            log.error("Error handling WebSocket connection event", e);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        try {
            String sessionId = extractSessionId(event);
            if (sessionId != null) {
                long newCount = activeUserService.removeUserAndGetCount(sessionId);
                webSocketController.broadcastUserCount((int) newCount);
                log.info("User disconnected: {}. New count: {}", sessionId, newCount);
            } else {
                log.warn("Failed to extract session ID during disconnection event.");
            }
        } catch (Exception e) {
            log.error("Error handling WebSocket disconnection event", e);
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
