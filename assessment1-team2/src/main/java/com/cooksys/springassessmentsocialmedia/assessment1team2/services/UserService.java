package com.cooksys.springassessmentsocialmedia.assessment1team2.services;


import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;

public interface UserService {

	List<TweetResponseDto> getAllMentions(String username);

	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	UserResponseDto getUserByUsername(String username);
	
	List<UserResponseDto> getActiveUsers();

    UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

	UserResponseDto deleteUser(String username, Credentials credentials);

    List<TweetResponseDto> getTweetsByAuthor(String username);

	CredentialsDto followUser(String userToFollow, CredentialsDto credentialsDto);

	List<UserResponseDto> getFollowing(String username);

	List<UserResponseDto> getFollowers(String username);

	List<TweetResponseDto> getFeed(String username);

}
