package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.ContextDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.HashtagDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotAuthorizedException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.CredentialsMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.HashtagMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.TweetMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.UserMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.HashtagRepository;
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
	private final HashtagRepository hashtagRepository;

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
		if (user.isEmpty()) {
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

		validateTweetAuthor(author.getCredentials());

		Tweet tweetReply = tweetMapper.requestDtoToEntity(tweetRequestDto);
		tweetReply.setAuthor(author);
		tweetReply.setInReplyTo(repliedToTweet);

		Tweet reply = tweetRepository.saveAndFlush(tweetReply);

		List<Tweet> replies = repliedToTweet.getReplies();
		replies.add(reply);
		repliedToTweet.setReplies(replies);
		tweetRepository.saveAndFlush(repliedToTweet);

		return tweetMapper.entityToDto(reply);
	}

	@Override
	public TweetResponseDto createRepostTweet(Long id, Credentials credentials) {
		Tweet tweetToRepost = findTweetById(id);

		validateTweetAuthor(credentials);

		Tweet repost = new Tweet();

		validateTweetAuthor(credentials);

		User author = validateTweetAuthor(credentials);

		repost.setAuthor(author);
		repost.setContent(tweetToRepost.getContent());
		repost.setRepostOf(tweetToRepost);
		System.out.println(repost.getRepostOf().getId());
		repost = tweetRepository.saveAndFlush(repost);
		System.out.println(repost.getRepostOf().getId());

		List<Tweet> reposts = new ArrayList<>(tweetToRepost.getReposts());

		if (!reposts.contains(repost)) {
			reposts.add(repost);
			tweetToRepost.setReposts(reposts);
			tweetRepository.saveAndFlush(tweetToRepost);
		}

		return tweetMapper.entityToDto(repost);
	}

	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAll());
	}

	public User getUser(Credentials credentials) {
		if(userRepository.findOneByCredentials(credentials).isPresent())
			return userRepository.findOneByCredentials(credentials).get();
	
		throw new NotFoundException("No user found with the username: " + credentials.getUsername()
				+ " or you are sending me an incorrect password");

	}

	public String createMention (String tweetBody) {
		String mentionUser;
		String[] arrOfStr = tweetBody.split("@",2);
		mentionUser = arrOfStr[1];
		String[] arrOfStr1 = mentionUser.split(" ",2);
		String mentionName=arrOfStr1[0];
		return mentionName;
		  
	}

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
			User tweeterAuthor = getUser(credentialsEnt);
			Tweet tweetToCreate = tweetMapper.requestDtoToEntity(tweetRequestDto);
			tweetToCreate.setAuthor(tweeterAuthor);
			tweetToCreate.setContent(tweetRequestDto.getContent());
			//Get mention in tweet
			String tweetContent = tweetToCreate.getContent();
			
			User mentioned = null;
			
			if(tweetContent.contains("@")) {
	
				int startIndex = tweetContent.indexOf("@") + 1;
				int endIndex = tweetContent.indexOf("#") - 1;
				String username = endIndex <= startIndex ? tweetContent.substring(startIndex) :
						tweetContent.substring(startIndex, endIndex);
				System.out.println(username);
				mentioned = findUserByUsername(username);

				List<User> mentionedUsers = new ArrayList<>();
				mentionedUsers.add(mentioned);
				tweetToCreate.setMentions(mentionedUsers);
			}

			Hashtag hashtagToCreate = null;
			List<Hashtag> hashtags = new ArrayList<>();
			if(tweetContent.contains("#")) {
	
				int startIndex = tweetContent.indexOf("#") + 1;
				String hashtag = tweetContent.substring(startIndex);
				System.out.println(hashtag);
				
				Optional<Hashtag> tagged = hashtagRepository.findByLabel(hashtag);
				
				if(tagged.isEmpty()) {
					hashtagToCreate = new Hashtag();
					hashtagToCreate.setLabel(hashtag);
					hashtagToCreate = hashtagRepository.saveAndFlush(hashtagToCreate);
					hashtags.add(hashtagToCreate);
					
				} else {
					hashtagToCreate = tagged.get();
					hashtags.add(hashtagToCreate);
				}

				tweetToCreate.setHashtags(hashtags);
			}

			Long id = tweetRepository.saveAndFlush(tweetToCreate).getId();
			Tweet createdTweet = tweetRepository.findById(id).orElse(null);

			if (mentioned != null) {
				List<Tweet> mentions = mentioned.getMentions();
				mentions.add(createdTweet);
				mentioned.setMentions(mentions);
				userRepository.saveAndFlush(mentioned);
			}

			if (hashtagToCreate != null) {
				for (Hashtag tag : hashtags) {
					List<Tweet> tweets = tag.getTweets();
					tweets.add(createdTweet);
					tag.setTweets(tweets);
					hashtagRepository.saveAndFlush(tag);
				}
				
			}
			return tweetMapper.entityToDto(createdTweet);
		}


	public List<TweetResponseDto> getFeed(User user, List<User> followedUsers) {
		List<Tweet> feed = new ArrayList<>(tweetRepository.findAllByAuthorAndDeletedFalse(user));
		for (User followedUser : followedUsers) {
			feed.addAll(tweetRepository.findAllByAuthorAndDeletedFalse(followedUser));
		}
		if (feed.size() > 1) feed.sort(Comparator.comparing(Tweet::getPosted));
		return tweetMapper.entitiesToDtos(feed);
	}

	@Override
	public List<TweetResponseDto> getRepostsByTweetId(Long id) {
		Tweet parentTweet = tweetRepository.findByIdAndDeletedFalse(id).orElse(null);
		if (parentTweet == null)
			throw new NotFoundException("The parent tweet does not exist");
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByRepostOf(parentTweet));
	}

	@Override
	public List<TweetResponseDto> getRepliesByTweetId(Long id) {
		Tweet parentTweet = tweetRepository.findByIdAndDeletedFalse(id).orElse(null);
		if (parentTweet == null)
			throw new NotFoundException("The parent tweet does not exist");
		return tweetMapper.entitiesToDtos(parentTweet.getReplies());
	}

	@Override
	public ContextDto getContextByTweetId(Long id) {
		ContextDto contextDto = new ContextDto();
		List<Tweet> before = new ArrayList<>();
		List<Tweet> after = new ArrayList<>();

		// Set target
		Tweet targetTweet = tweetRepository.findByIdAndDeletedFalse(id).isPresent()
				? tweetRepository.findByIdAndDeletedFalse(id).get()
				: null;
		contextDto.setTarget(tweetMapper.entityToDto(targetTweet));

		// Set before
		if (targetTweet == null)
			throw new NotFoundException("The target tweet does not exist");
		Tweet parentTweet = targetTweet.getInReplyTo();
		while (parentTweet != null) {
			if (!parentTweet.isDeleted())
				before.add(parentTweet);
			parentTweet = parentTweet.getInReplyTo();
		}
		if (before.size() > 1)
			Collections.reverse(before);
		contextDto.setBefore(tweetMapper.entitiesToDtos(before));

		// Set after
		for (Tweet reply : targetTweet.getReplies()) {
			if (reply.isDeleted())
				continue;
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
			if (like.isDeleted())
				continue;
			likes.add(like);
		}
		return userMapper.entitiesToDtos(likes);
	}

	@Override
	public List<HashtagDto> getTagsByTweetId(Long id) {
		Tweet tweet = findTweetById(id);
		return hashtagMapper.entitiesToDto(tweet.getHashtags());
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, Credentials credentials) {

		Tweet tweetToDelete = findTweetById(id);

		User author = findUserByUsername(credentials.getUsername());

		if (validateTweetAuthor(credentials) != author) {
			throw new NotAuthorizedException("You are not authorized to delete this tweet.");
		} else {
			tweetToDelete.setDeleted(true);
		}
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweetToDelete));
	}

	@Override
	public void likeTweet(Long id, CredentialsDto credentialsDto) {
		User user = userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername());
		if (user == null) throw new NotFoundException("Only registered users can like tweets");
		Tweet tweet = findTweetById(id);

		// Add user to tweet likes
		List<User> likes = tweet.getLikes();
		likes.add(user);
		tweet.setLikes(likes);
		tweetRepository.saveAndFlush(tweet);

		// Add tweet to user likedTweets
		List<Tweet> likedTweets = user.getLikedTweet();
		likedTweets.add(tweet);
		user.setLikedTweet(likedTweets);
		userRepository.saveAndFlush(user);
	}

}
