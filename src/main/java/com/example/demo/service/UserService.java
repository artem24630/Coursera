package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDatabaseInterface userRepository;

    private final PasswordEncoder encoder;

    public UserService(UserDatabaseInterface userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id)
                .map(usr -> new UserDto(usr.getId(), usr.getUsername(), "", usr.getRoles()));
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        userRepository.save(new User(userDto.getId(),
                userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                userDto.getRoles()
        ));
    }
}
