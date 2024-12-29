package com.raju.medium.active_users_display.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Broadcast the active user count to all connected clients
    public void broadcastUserCount(int activeUsers) {
        messagingTemplate.convertAndSend("/topic/activeUsers", activeUsers);
    }
}

