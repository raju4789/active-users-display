package com.raju.medium.active_users_display.service;

public interface ActiveUserService {

    long getActiveUserCount();
    long addUserAndGetCount(String sessionId);
    long removeUserAndGetCount(String sessionId);
    boolean isServerCapacityExceeded();
}
