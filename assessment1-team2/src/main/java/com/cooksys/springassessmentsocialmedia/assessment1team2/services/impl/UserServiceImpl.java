package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.UserMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getActiveUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByCredentials_UsernameIs(username);
        if (user == null || user.isDeleted())
            throw new NotFoundException("No user exists with username " + username);
        return userMapper.entityToDto(user);
    }
}
