package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getActiveUsers();

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);
}
