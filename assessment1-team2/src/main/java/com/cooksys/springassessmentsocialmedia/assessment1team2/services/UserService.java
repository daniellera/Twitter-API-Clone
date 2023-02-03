package com.cooksys.springassessmentsocialmedia.assessment1team2.services;


import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;

import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;

public interface UserService {

	List<TweetResponseDto> getAllMentions(String username);

	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	UserResponseDto getUserByUsername(String username);
	
	List<UserResponseDto> getActiveUsers();

    UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

	UserResponseDto deleteUser(String username, Credentials credentials);

    List<TweetResponseDto> getTweetsByAuthor(String username);
}
