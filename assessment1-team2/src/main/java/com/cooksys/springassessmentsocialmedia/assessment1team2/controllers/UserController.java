package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.TweetService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final TweetService tweetService;

	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}

	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody Credentials credentials) {
		return userService.deleteUser(username, credentials);
	}

	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getAllMentions(@PathVariable String username) {
		return userService.getAllMentions(username);
	}

	@GetMapping
	public List<UserResponseDto> getActiveUsers() {
		return userService.getActiveUsers();
	}

	@GetMapping("/@{username}")
	public UserResponseDto getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}

	@PatchMapping("/@{username}")
	public UserResponseDto updateUserProfile(@PathVariable String username,
			@RequestBody UserRequestDto userRequestDto) {
		return userService.updateUserProfile(username, userRequestDto);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getTweetsByAuthor(@PathVariable String username) {
		return userService.getTweetsByAuthor(username);
	}

	@PostMapping("/@{userToFollow}/follow")
	public CredentialsDto followUser(@PathVariable String userToFollow, @RequestBody CredentialsDto credentialsDto) {
		return userService.followUser(userToFollow, credentialsDto);
	}

	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}
	@PostMapping("/@{userToFollow}/unfollow")
	public CredentialsDto unfollowUser(@PathVariable String userToFollow, @RequestBody CredentialsDto credentialsDto) {
		return userService.unfollowUser(userToFollow, credentialsDto);
	}


}
