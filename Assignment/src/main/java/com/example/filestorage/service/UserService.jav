package com.example.filestorage.service;

import com.example.filestorage.model.User;
import com.example.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
