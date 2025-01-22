package com.example.chat.dto;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private String sender;
    private String content;
    private String type;    // CHAT, JOIN, LEAVE
    private String roomId;
    private LocalDateTime timestamp;

    public ChatMessageDto() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessageDto(String sender, String content, String type, String roomId) {
        this();
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.roomId = roomId;
    }

    // Getters and Setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
} 