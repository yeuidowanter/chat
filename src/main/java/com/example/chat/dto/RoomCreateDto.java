package com.example.chat.dto;

public class RoomCreateDto {
    private String roomName;
    private String owner;

    // 기본 생성자
    public RoomCreateDto() {}

    // Getter와 Setter
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
} 