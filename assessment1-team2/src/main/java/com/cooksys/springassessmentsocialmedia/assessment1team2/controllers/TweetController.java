package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.ContextDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
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
	public List<UserResponseDto> getAllMentions(@PathVariable Long id){
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
	public List<TweetResponseDto> getAllTweets(){
		return tweetService.getAllTweets();
	}
	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
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

}
