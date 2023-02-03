package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.ContextDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

import java.util.List;

public interface TweetService {

	List<UserResponseDto> getAllMentions(Long id);

	TweetResponseDto getTweetById(Long id);

    List<TweetResponseDto> getTweetsByAuthor(User author);

	TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> getFeed(User user, List<User> followedUsers);

	List<TweetResponseDto> getRepostsByTweetId(Long id);

	List<TweetResponseDto> getRepliesByTweetId(Long id);

	ContextDto getContextByTweetId(Long id);

	List<UserResponseDto> getLikesByTweetId(Long id);
}
