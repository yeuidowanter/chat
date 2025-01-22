package com.example.chat.repository;

import com.example.chat.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByIsActiveTrue();
}