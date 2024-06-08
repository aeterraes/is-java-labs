package org.example.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dataaccess.dto.UserDto;
import org.example.dataaccess.entity.User;
import org.example.service.services.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final CustomUserDetailsService userService;
    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public void createUser(@RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        userService.createUser(user);
    }

    @GetMapping("/{username}")
    public UserDto findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}

