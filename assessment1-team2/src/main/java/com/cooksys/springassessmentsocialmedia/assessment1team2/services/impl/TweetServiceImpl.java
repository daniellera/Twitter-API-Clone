package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.util.*;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.*;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.HashtagMapper;
import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotAuthorizedException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.CredentialsMapper;
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
	private final HashtagMapper hashtagMapper;

	// private method built to find a tweet and handle errors if tweet is deleted.
	private Tweet findTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with ID: " + id + " not found.");
		}
		return optionalTweet.get();
	}
	
	private User validateTweetAuthor(CredentialsDto credentialsDto) {
		User validateUser = userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername());
		if(validateUser.getCredentials().getUsername().isEmpty()) {
			throw new NotAuthorizedException("Credentials do not match.");
		}
		if(validateUser.isDeleted()) {
			throw new NotAuthorizedException("User not found with those credentials.");
		}
		return validateUser;
	}
	
	private void validateTweet(TweetRequestDto tweetRequestDto) {
		
		if(tweetRequestDto.getContent() == null) {
			throw new BadRequestException("You cannot submit an empty tweet.");
		}
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
		
		User author = validateTweetAuthor(tweetRequestDto.getCredentials());
		
		Tweet reply = tweetMapper.requestDtoToEntity(tweetRequestDto);
		
		List<Tweet> replies = repliedToTweet.getReplies();
		
		reply.setAuthor(author);
		reply.setInReplyTo(repliedToTweet);
		replies.add(reply);
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
		
	}

	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAll());
	}

	public User getUser(Credentials credentials) {
		List<User> user =userRepository.findAll();
		for(int i =0; i< user.size();i++) {
			if(user.get(i).getCredentials().getUsername().equals( credentials.getUsername())&& user.get(i).getCredentials().getPassword().equals (credentials.getPassword())) {
				user.get(i).setCredentials(credentials);
				return user.get(i);
			}
		}
		
			throw new NotFoundException("No user found with the username: " + credentials.getUsername() + " or you are sending me an incorrect password");
		
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
		if(tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new NotFoundException("Your tweet failed to send make sure you have your credentials and the tweet!");
		}
		CredentialsDto credentials = tweetRequestDto.getCredentials(); 
		Credentials credentialsEnt = credentialsMapper.dtoToEntity(credentials);
		//make a getUsers method to verify that the credentials given have a user in the database.
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

	public List<TweetResponseDto> getFeed(User user, List<User> followedUsers) {
		List<Tweet> feed = new ArrayList<>();
		feed.addAll(tweetRepository.findAllByAuthorAndDeletedFalse(user));
		for (User followedUser : followedUsers) {
			feed.addAll(tweetRepository.findAllByAuthorAndDeletedFalse(followedUser));
		}
		if (feed.size() == 0) throw new NotFoundException("Feed is empty");
		feed.sort(Comparator.comparing(Tweet::getPosted));
		return tweetMapper.entitiesToDtos(feed);
	}

	@Override
	public List<TweetResponseDto> getRepostsByTweetId(Long id) {
		Tweet parentTweet = tweetRepository.findByIdAndDeletedFalse(id).isPresent() ?
				tweetRepository.findByIdAndDeletedFalse(id).get() : null;
		if (parentTweet == null) throw new NotFoundException("The parent tweet does not exist");
		return tweetMapper.entitiesToDtos(parentTweet.getReposts());
	}

	@Override
	public List<TweetResponseDto> getRepliesByTweetId(Long id) {
		Tweet parentTweet = tweetRepository.findByIdAndDeletedFalse(id).isPresent() ?
				tweetRepository.findByIdAndDeletedFalse(id).get() : null;
		if (parentTweet == null) throw new NotFoundException("The parent tweet does not exist");
		return tweetMapper.entitiesToDtos(parentTweet.getReplies());
	}

	@Override
	public ContextDto getContextByTweetId(Long id) {
		ContextDto contextDto = new ContextDto();
		List<Tweet> before = new ArrayList<>();
		List<Tweet> after = new ArrayList<>();

		// Set target
		Tweet targetTweet = tweetRepository.findByIdAndDeletedFalse(id).isPresent() ?
				tweetRepository.findByIdAndDeletedFalse(id).get() : null;
		contextDto.setTarget(tweetMapper.entityToDto(targetTweet));

		// Set before
		if (targetTweet == null) throw new NotFoundException("The target tweet does not exist");
		Tweet parentTweet = targetTweet.getInReplyTo();
		while (parentTweet != null) {
			if (!parentTweet.isDeleted()) before.add(parentTweet);
			parentTweet = parentTweet.getInReplyTo();
		}
		if (before.size() > 1) Collections.reverse(before);
		contextDto.setBefore(tweetMapper.entitiesToDtos(before));

		// Set after
		for (Tweet reply : targetTweet.getReplies()) {
			if (reply.isDeleted()) continue;
			after.add(reply);
		}
		contextDto.setAfter(tweetMapper.entitiesToDtos(after));

		return contextDto;
	}

	@Override
	public List<UserResponseDto> getLikesByTweetId(Long id) {
		Tweet tweet = findTweetById(id);
		List<User> likes = new ArrayList<>();
		for (User like : tweet.getLikes()) {
			if (like.isDeleted()) continue;
			likes.add(like);
		}
		return userMapper.entitiesToDtos(likes);
	}

	@Override
	public List<HashtagDto> getTagsByTweetId(Long id) {
		Tweet tweet = findTweetById(id);
		return hashtagMapper.entitiesToDto(tweet.getHashtags());
	}

}
