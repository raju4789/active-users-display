package com.raju.medium.active_users_display.controller;

import com.raju.medium.active_users_display.service.ActiveUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final ActiveUserService activeUserService;

    public HomeController(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}

@RestController
class ActiveUserApiController {

    private final ActiveUserService activeUserService;

    public ActiveUserApiController(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @GetMapping("/api/active-users/count")
    public ResponseEntity<Map<String, Object>> getActiveUserCount() {
        Map<String, Object> response = new HashMap<>();
        long count = activeUserService.getActiveUserCount();
        response.put("count", count);
        response.put("capacityExceeded", activeUserService.isServerCapacityExceeded());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}

