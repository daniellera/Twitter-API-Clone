package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.*;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

public interface TweetService {

	List<UserResponseDto> getAllMentions(Long id);

	TweetResponseDto getTweetById(Long id);

	List<TweetResponseDto> getTweetsByAuthor(User author);

	TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto createRepostTweet(Long id, Credentials credentials);

	List<TweetResponseDto> getFeed(User user, List<User> followedUsers);

	List<TweetResponseDto> getRepostsByTweetId(Long id);

	List<TweetResponseDto> getRepliesByTweetId(Long id);

	ContextDto getContextByTweetId(Long id);

	List<UserResponseDto> getLikesByTweetId(Long id);

	List<HashtagDto> getTagsByTweetId(Long id);

	TweetResponseDto deleteTweet(Long id, Credentials credentials);

	void likeTweet(Long id, CredentialsDto credentialsDto);

}
