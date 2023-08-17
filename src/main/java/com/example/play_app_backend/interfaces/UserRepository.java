package com.example.play_app_backend.interfaces;

import com.example.play_app_backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
    void deleteByUsername(String username);
}
