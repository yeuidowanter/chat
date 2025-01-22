package com.example.chat.dto;

import com.example.chat.domain.Room;
import java.time.LocalDateTime;
import java.util.Set;

public class RoomDto {
    private String roomId;
    private String roomName;
    private Set<String> members;
    private String owner;
    private int maxMembers;
    private LocalDateTime createdAt;
    private boolean isActive;

    // 기본 생성자
    public RoomDto() {}

    // Room 엔티티를 DTO로 변환하는 정적 메서드
    public static RoomDto from(Room room) {
        RoomDto dto = new RoomDto();
        dto.setRoomId(room.getRoomId());
        dto.setRoomName(room.getRoomName());
        dto.setMembers(room.getMembers());
        dto.setOwner(room.getOwner());
        dto.setMaxMembers(room.getMaxMembers());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setActive(room.isActive());
        return dto;
    }

    // Getter와 Setter
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public Set<String> getMembers() { return members; }
    public void setMembers(Set<String> members) { this.members = members; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public int getMaxMembers() { return maxMembers; }
    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
} 