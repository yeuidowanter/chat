package com.example.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    
    private String content;
    private String sender;
    private String type;    // CHAT, JOIN, LEAVE
    private LocalDateTime timestamp;
    private String roomId;  // Room 참조 대신 roomId 사용

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String content, String sender, String type, String roomId) {
        this();
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.roomId = roomId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
} 