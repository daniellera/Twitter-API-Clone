package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @GetMapping("/@{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

}
