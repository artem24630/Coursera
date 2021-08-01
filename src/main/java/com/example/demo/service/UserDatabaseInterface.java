package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDatabaseInterface {
    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    void deleteById(long id);
}
