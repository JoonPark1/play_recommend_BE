package com.example.play_app_backend.services;

import com.example.play_app_backend.interfaces.UserRepository;
import com.example.play_app_backend.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository u){
        this.userRepository = u;
    }

    public void addNewUser(User user){
        this.userRepository.save(user);
    }

    public User findUserByUsernameAndPassword(String username, String password){
        return this.userRepository.findByUsernameAndPassword(username, password);
    }

    public void deleteUserByUsername(String username){
        this.userRepository.deleteByUsername(username);
    }
}
