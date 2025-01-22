package com.example.chat.controller;

import com.example.chat.domain.Room;
import com.example.chat.dto.RoomCreateDto;
import com.example.chat.dto.RoomDto;
import com.example.chat.dto.RoomMemberDto;
import com.example.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public RoomDto createRoom(@RequestBody RoomCreateDto request) {
        Room room = roomService.createRoom(request.getRoomName(), request.getOwner());
        return RoomDto.from(room);
    }

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRoomDtos();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable String roomId) {
        try {
            RoomDto room = roomService.getRoom(roomId);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<RoomDto> addMember(@PathVariable String roomId, @RequestBody RoomMemberDto request) {
        try {
            RoomDto updatedRoom = roomService.addMemberToRoom(roomId, request.getUsername());
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{roomId}/members/{username}")
    public ResponseEntity<?> removeMember(@PathVariable String roomId, @PathVariable String username) {
        if (roomService.removeMemberFromRoom(roomId, username)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
} 