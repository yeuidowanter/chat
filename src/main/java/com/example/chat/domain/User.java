package com.example.chat.domain;

public class User {
    private String userId;
    private String username;
    private String status;  // "ONLINE" 또는 "OFFLINE"

    public User() {
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.status = "ONLINE";
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 