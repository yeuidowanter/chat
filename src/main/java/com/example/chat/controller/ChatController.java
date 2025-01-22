package com.example.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.chat.domain.Message;
import com.example.chat.repository.MessageRepository;
import com.example.chat.domain.ChatMessage;
import com.example.chat.service.RoomService;
import java.util.List;
import com.example.chat.dto.ChatMessageDto;
import com.example.chat.service.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
public class ChatController {
    
    private final MessageService messageService;
    private final RoomService roomService;
    
    @Autowired
    public ChatController(MessageService messageService, RoomService roomService) {
        this.messageService = messageService;
        this.roomService = roomService;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessageDto chat(@DestinationVariable String roomId, ChatMessageDto chatMessage) {
        chatMessage.setRoomId(roomId);
        messageService.saveMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/join/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessageDto join(@DestinationVariable String roomId, ChatMessageDto joinMessage) {
        joinMessage.setRoomId(roomId);
        
        try {
            if (roomService.canJoinRoom(roomId, joinMessage.getSender())) {
                messageService.saveMessage(joinMessage);
                return joinMessage;
            }
        } catch (RuntimeException e) {
            roomService.createRoom("Chat Room " + roomId, joinMessage.getSender());
            messageService.saveMessage(joinMessage);
            return joinMessage;
        }
        return null;
    }

    @MessageMapping("/leave/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessageDto leave(ChatMessageDto leaveMessage) {
        messageService.saveMessage(leaveMessage);
        return leaveMessage;
    }

    @GetMapping("/api/messages/{roomId}")
    @ResponseBody
    public List<ChatMessageDto> getRoomMessages(@PathVariable String roomId) {
        return messageService.getRoomMessages(roomId);
    }
} 