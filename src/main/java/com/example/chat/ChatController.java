package com.example.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

    private Set<User> users = new ConcurrentHashMap<String, User>().newKeySet();

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Message chat(Message message) {
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    @MessageMapping("/join")
    @SendTo("/topic/chat")
    public Message join(User user) {
        users.add(user);
        Message message = new Message();
        message.setType("JOIN");
        message.setContent(user.getUsername() + " has joined the chat.");
        message.setSender("System");
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/chat")
    public Message leave(User user) {
        users.removeIf(u -> u.getUserId().equals(user.getUserId()));
        Message message = new Message();
        message.setType("LEAVE");
        message.setContent(user.getUsername() + " has left the chat.");
        message.setSender("System");
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }

    @GetMapping("/users")
    @ResponseBody
    public Set<User> getUsers() {
        return users;
    }
} 