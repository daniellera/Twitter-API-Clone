package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;

public interface TweetService {

	List<UserResponseDto> getAllMentions(Long id);

	

}
