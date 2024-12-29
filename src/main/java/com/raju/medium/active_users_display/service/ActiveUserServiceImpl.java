package com.raju.medium.active_users_display.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveUserServiceImpl implements ActiveUserService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String ACTIVE_USERS_KEY = "activeUsers";

    // Constructor injection ensures redisTemplate is properly initialized by Spring
    public ActiveUserServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Get the number of active users
    public int getActiveUserCount() {
        return Math.toIntExact(redisTemplate.opsForSet().size(ACTIVE_USERS_KEY));
    }

    // Add a user session to the active users set
    public void addUser(String sessionId) {
        redisTemplate.opsForSet().add(ACTIVE_USERS_KEY, sessionId);
    }

    // Remove a user session from the active users set
    public void removeUser(String sessionId) {
        redisTemplate.opsForSet().remove(ACTIVE_USERS_KEY, sessionId);
    }
}

