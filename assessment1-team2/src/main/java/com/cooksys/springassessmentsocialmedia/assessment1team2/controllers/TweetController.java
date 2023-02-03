package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.*;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	private final TweetService tweetService;

	@GetMapping("/{id}/mentions")
	public List<UserResponseDto> getAllMentions(@PathVariable Long id) {
		return tweetService.getAllMentions(id);
	}

	@GetMapping("/{id}")
	public TweetResponseDto getTweetById(@PathVariable Long id) {
		return tweetService.getTweetById(id);
	}

	@PostMapping("/{id}/reply")
	public TweetResponseDto createReplyTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createReplyTweet(id, tweetRequestDto);
	}

	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}

	@PostMapping("/{id}/repost")
	public TweetResponseDto createRepostTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
		return tweetService.createRepostTweet(id, credentials);
	}

	@GetMapping("{id}/reposts")
	public List<TweetResponseDto> getRepostsByTweetId(@PathVariable Long id) {
		return tweetService.getRepostsByTweetId(id);
	}

	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getRepliesByTweetId(@PathVariable Long id) {
		return tweetService.getRepliesByTweetId(id);
	}

	@GetMapping("/{id}/context")
	public ContextDto getContextByTweetId(@PathVariable Long id) {
		return tweetService.getContextByTweetId(id);
	}

	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getLikesByTweetId(@PathVariable Long id) {
		return tweetService.getLikesByTweetId(id);
	}

	@GetMapping("/{id}/tags")
	public List<HashtagDto> getTagsByTweetId(@PathVariable Long id) {
		return tweetService.getTagsByTweetId(id);
	}
	
	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
		return tweetService.deleteTweet(id, credentials);
	}

	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
		tweetService.likeTweet(id, credentialsDto);
	}

}
