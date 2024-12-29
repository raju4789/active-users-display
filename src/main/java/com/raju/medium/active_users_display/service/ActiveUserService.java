package com.raju.medium.active_users_display.service;

public interface ActiveUserService {

    int getActiveUserCount();
    void addUser(String sessionId);
    void removeUser(String sessionId);
}
