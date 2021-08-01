package com.example.demo.controller;


import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private RoleRepository roleRepository;
    private UserService userService;

    @Autowired
    public UserController(RoleRepository roleRepository, UserService userService)
    {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleRepository.findAll();
    }

    @RequestMapping("/new")
    public String userForm(Model model){
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @PostMapping
    public String submitUserForm(@Valid @ModelAttribute("user") UserDto user,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }
}
