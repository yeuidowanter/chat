package com.example.chat.repository;

import com.example.chat.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByStatus(String status);
    boolean existsByUsername(String username);
} 