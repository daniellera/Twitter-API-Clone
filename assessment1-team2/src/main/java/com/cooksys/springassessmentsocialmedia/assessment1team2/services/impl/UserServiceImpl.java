package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.CredentialsMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.ProfileMapper;
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
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;

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

    @Override
    public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
        User userToUpdate = userRepository.findByCredentials_UsernameIs(username);
        Credentials credentials = credentialsMapper.dtoToEntity(userRequestDto.getCredentials());
        Profile profile = profileMapper.dtoToEntity(userRequestDto.getProfile());
        if (userToUpdate == null || userToUpdate.isDeleted())
            throw new NotFoundException("No user exists with username " + username);
        if (!userToUpdate.getCredentials().equals(credentials))
            throw new BadRequestException("Incorrect username and/or password provided");
        userToUpdate.setProfile(profile);
        return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
    }
}
