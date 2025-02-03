package com.example.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    private String status;  // ONLINE, OFFLINE
    
    private LocalDateTime lastLoginAt;
    
    private LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.status = "OFFLINE";
    }

    public User(String username) {
        this();
        this.username = username;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }

    // 비즈니스 로직
    public void login() {
        this.status = "ONLINE";
        this.lastLoginAt = LocalDateTime.now();
    }

    public void logout() {
        this.status = "OFFLINE";
    }
} 