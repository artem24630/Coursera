package com.example.demo.dto;

import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotNull
    @NotBlank(message="Username mustn't be null")
    private String username;

    private Set<Course> courses;

    private Set<Role> roles;

    @NotNull
    @NotBlank(message="Password mustn't be null")
    private String password;

    public UserDto(String username) {
        this.username = username;
    }

    public UserDto(Long id, String username, String password, Set<Role> roles){
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
