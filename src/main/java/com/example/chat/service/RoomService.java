package com.example.chat.service;

import com.example.chat.dto.RoomDto;
import com.example.chat.domain.Room;
import com.example.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String roomName, String owner) {
        String roomId = "room-" + UUID.randomUUID().toString();
        Room room = new Room(roomId, roomName, owner);
        return roomRepository.save(room);
    }

    public RoomDto getRoom(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return RoomDto.from(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findByIsActiveTrue();
    }

    public RoomDto addMemberToRoom(String roomId, String username) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        boolean added = room.addMember(username);
        if (added) {
            room = roomRepository.save(room);
            return RoomDto.from(room);
        }
        throw new RuntimeException("Failed to add member to room");
    }

    public boolean canJoinRoom(String roomId, String username) {
        RoomDto room = getRoom(roomId);
        return room.isActive() && room.getMembers().contains(username);
    }

    public boolean removeMemberFromRoom(String roomId, String username) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        boolean removed = room.removeMember(username);
        if (removed) {
            roomRepository.save(room);
        }
        return removed;
    }

    public List<RoomDto> getAllRoomDtos() {
        return roomRepository.findByIsActiveTrue()
                .stream()
                .map(RoomDto::from)
                .collect(Collectors.toList());
    }
}