package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotAuthorizedException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.CredentialsMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.ProfileMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.TweetMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.UserMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.TweetRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;

	// private method built to find a tweet and handle errors if tweet is deleted.
	private Tweet findTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with ID: " + id + " not found.");
		}
		return optionalTweet.get();
	}

	private User validateTweetAuthor(Credentials credentials) {
		Optional<User> validateUser = userRepository.findOneByCredentials(credentials);
		if (validateUser.isEmpty()) {
			throw new NotAuthorizedException("Credentials do not match.");
		}
		if (validateUser.get().isDeleted()) {
			throw new NotAuthorizedException("User not found with those credentials.");
		}
		return validateUser.get();
	}

	private void validateTweet(TweetRequestDto tweetRequestDto) {

		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("You cannot submit an empty tweet.");
		}
	}
	
	private User findUserByUsername(String username) {
		Optional<User> user = userRepository.findByCredentialsUsername(username);
		if(user.isEmpty()) {
			throw new NotFoundException("User not found with that username");
		}
		return user.get();
	}

	// place holder for now. Need to implement fully once other post methods are
	// completed.
	@Override
	public List<UserResponseDto> getAllMentions(Long id) {
		Tweet tweets = findTweetById(id);
		if (tweets == null) {
			throw new NotFoundException("No tweets found.");
		}
		List<User> mentions = tweets.getMentions();

		return userMapper.entitiesToDtos(mentions);
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {

		Tweet tweet = findTweetById(id);

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<TweetResponseDto> getTweetsByAuthor(User author) {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByAuthorOrderByPostedDesc(author));
	}

	@Override
	public TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto) {

		Tweet repliedToTweet = findTweetById(id);

		validateTweet(tweetRequestDto);

		User author = findUserByUsername(tweetRequestDto.getCredentials().getUsername());
		
		validateTweetAuthor(credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials()));
		
		author.setCredentials(credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials()));

		Tweet reply = tweetMapper.dtoToEntity(tweetRequestDto);


		List<Tweet> replies = repliedToTweet.getReplies();
		System.out.println(reply);
		reply.setAuthor(author);
		reply.setContent(tweetRequestDto.getContent());
		reply.setInReplyTo(repliedToTweet);
		replies.add(reply);
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
	}

	@Override
	public TweetResponseDto createRepostTweet(Long id, Credentials credentials) {
		Tweet repostedTweet = findTweetById(id);
		
		TweetRequestDto tweet = tweetMapper.entityToDtoRequest(repostedTweet);

		validateTweetAuthor(credentials);
		
		Tweet repost = tweetMapper.dtoToEntity(tweet);
		
		User author = findUserByUsername(credentials.getUsername());

		repost.setAuthor(author);
		repost.setContent(repostedTweet.getContent());
		repost.setRepostOf(repostedTweet);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}

	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAll());
	}

	public User getUser(Credentials credentials) {
		List<User> user = userRepository.findAll();
		for (int i = 0; i < user.size(); i++) {
			if (user.get(i).getCredentials().getUsername().equals(credentials.getUsername())
					&& user.get(i).getCredentials().getPassword().equals(credentials.getPassword())) {
				user.get(i).setCredentials(credentials);
				return user.get(i);
			}
		}

		throw new NotFoundException("No user found with the username: " + credentials.getUsername()
				+ " or you are sending me an incorrect password");

	}

//	public String createMention (String tweetBody) {
//		String mentionUser;
//		String[] arrOfStr = tweetBody.split("@",2);
//		mentionUser = arrOfStr[1];
//		String[] arrOfStr1 = mentionUser.split(" ",2);
//		String mentionName=arrOfStr1[0];
//		return mentionName;
//		  
//	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null
				|| tweetRequestDto.getCredentials() == null) {
			throw new NotFoundException("Your tweet failed to send make sure you have your credentials and the tweet!");
		}
		CredentialsDto credentials = tweetRequestDto.getCredentials();
		Credentials credentialsEnt = credentialsMapper.dtoToEntity(credentials);
		// make a getUsers method to verify that the credentials given have a user in
		// the database.
		User tweeter = getUser(credentialsEnt);
		Tweet tweetToCreate = tweetMapper.dtoToEntity(tweetRequestDto);
		tweetToCreate.setAuthor(tweeter);
		String tweetContent = tweetToCreate.getContent();
//		String mention;
//		if(tweetContent.contains("@")) {
//			mention = createMention (tweetContent);
//		}
//		List<String> mentions;
//		mentions.add(mention);
//		tweeter.setMentions(mentions);
		Tweet tweetCreated = tweetRepository.saveAndFlush(tweetToCreate);
		return tweetMapper.entityToDto(tweetCreated);
	}

}
