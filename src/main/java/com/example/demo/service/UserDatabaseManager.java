package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDatabaseManager {
    private final UserRepository userRepository;

    @Autowired
    public UserDatabaseManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
