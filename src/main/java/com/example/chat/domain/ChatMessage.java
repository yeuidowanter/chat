package com.example.chat.domain;

public class ChatMessage {
    private String sender;
    private String content;
    private String type;    // CHAT, JOIN, LEAVE
    private String roomId;
    private long timestamp;

    public ChatMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public ChatMessage(String sender, String content, String type, String roomId) {
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
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
} 