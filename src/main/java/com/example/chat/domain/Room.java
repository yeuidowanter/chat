package com.example.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "rooms")
public class Room {
    @Id
    private String roomId;
    
    private String roomName;
    
    private Set<String> members = new HashSet<>();
    
    private String owner;
    
    private int maxMembers;
    
    private LocalDateTime createdAt;
    
    private boolean isActive;

    protected Room() {
        this.maxMembers = 10;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public Room(String roomId, String roomName, String owner) {
        this();
        this.roomId = roomId;
        this.roomName = roomName;
        this.owner = owner;
        this.members.add(owner);
    }

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public Set<String> getMembers() { return new HashSet<>(members); }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public int getMaxMembers() { return maxMembers; }
    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // 비즈니스 로직
    public boolean addMember(String username) {
        if (members.size() < maxMembers) {
            return members.add(username);
        }
        return false;
    }

    public boolean removeMember(String username) {
        return members.remove(username);
    }

    public boolean isMember(String username) {
        return members.contains(username);
    }
}