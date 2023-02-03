package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

public interface TweetService {

	List<UserResponseDto> getAllMentions(Long id);

	TweetResponseDto getTweetById(Long id);

    List<TweetResponseDto> getTweetsByAuthor(User author);

	TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

}
