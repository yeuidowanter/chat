package com.example.chat.service;

import com.example.chat.domain.Message;
import com.example.chat.dto.ChatMessageDto;
import com.example.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(ChatMessageDto chatMessageDto) {
        Message message = new Message(
            chatMessageDto.getContent(),
            chatMessageDto.getSender(),
            chatMessageDto.getType(),
            chatMessageDto.getRoomId()
        );
        messageRepository.save(message);
    }

    public List<ChatMessageDto> getRoomMessages(String roomId) {
        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId)
            .stream()
            .map(message -> new ChatMessageDto(
                message.getSender(),
                message.getContent(),
                message.getType(),
                message.getRoomId()
            ))
            .collect(Collectors.toList());
    }
} 