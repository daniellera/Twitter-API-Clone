package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.BadRequestException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotAuthorizedException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.CredentialsMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.ProfileMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.TweetMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.UserMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.TweetService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	private final ValidateService validateService;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;
	private final TweetService tweetService;
	
	private User findByUsername(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
		
		if(optionalUser.isEmpty()) {
			throw new NotFoundException("User with " + username + " not found. Please check spelling and try again.");
		}
		return optionalUser.get();
	}
	
	private User credentialCheck(Credentials credentials) {
		User checkedUser = userRepository.findByCredentials_UsernameIs(credentials.getUsername());
		if(checkedUser.getCredentials().getUsername().isEmpty()) {
			throw new NotAuthorizedException("Invalid credentials, please try again.");
		} else if (checkedUser.isDeleted()) {
			throw new NotAuthorizedException("User is already deleted.");
		}
		return checkedUser;
	}
	
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		
		User userToCreate = userMapper.dtoToEntity(userRequestDto);
		userToCreate.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
		userToCreate.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
		
			
		if(userToCreate.getCredentials() == null) {
			throw new BadRequestException("Please enter username, password, and email.");
		}
		if(userToCreate.getCredentials().getUsername() == null) {
			throw new BadRequestException("Username is required. Please enter a username and try again.");
		}
		
		if(userToCreate.getCredentials().getPassword() == null) {
			throw new BadRequestException("A password is required. Please enter a password and try again.");
		}
		
		if(userToCreate.getProfile() == null) {
			throw new BadRequestException("Please enter a valid email.");
		}
		if(userToCreate.getProfile().getEmail() == null) {
			throw new BadRequestException("Email is a required field. Please enter a valid email and try again.");
		}
		
		if(userToCreate.isDeleted()) {
			userToCreate.setDeleted(false);
			return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
		}
		
		boolean available = validateService.available(userToCreate.getCredentials().getUsername());
		
		boolean deleted = validateService.deleted(userToCreate.getCredentials().getUsername());

		if(!available) {
			throw new BadRequestException("Username is already taken. Please choose another and try again.");
		} else if(deleted) {
			User setDeleted = userRepository.findByCredentials_UsernameIs(userToCreate.getCredentials().getUsername());
			setDeleted.setDeleted(false);
			return userMapper.entityToDto(userRepository.saveAndFlush(setDeleted));
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
		
	}
	
	@Override
	public UserResponseDto deleteUser(String username, Credentials credentials) {
		
		User deleteUser = credentialCheck(credentials);
		deleteUser.setDeleted(true);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(deleteUser));
	}

	@Override
	public List<TweetResponseDto> getTweetsByAuthor(String username) {
		return tweetService.getTweetsByAuthor(userRepository.findByCredentials_UsernameIs(username));
	}

	@Override
	public List<TweetResponseDto> getAllMentions(String username) {
		
		User user = findByUsername(username);
		List<Tweet> mentions = user.getMentions();
		
		
		return tweetMapper.entitiesToDtos(mentions);
	}


    @Override
    public List<UserResponseDto> getActiveUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByCredentials_UsernameIs(username);
        if (user == null || user.isDeleted())
            throw new NotFoundException("No user exists with username " + username);
        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
        User userToUpdate = userRepository.findByCredentials_UsernameIs(username);
        Credentials credentials = credentialsMapper.dtoToEntity(userRequestDto.getCredentials());
        Profile profile = profileMapper.dtoToEntity(userRequestDto.getProfile());
        if (userToUpdate == null || userToUpdate.isDeleted())
            throw new NotFoundException("No user exists with username " + username);
        if (!userToUpdate.getCredentials().equals(credentials))
            throw new BadRequestException("Incorrect username and/or password provided");
        userToUpdate.setProfile(profile);
        return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
    }
    @Override
	public CredentialsDto followUser(String userToFollow, CredentialsDto credentialsDto) {
		List<User> user = userRepository.findAll();
		int userToFollowIndex = 0;
		int followerIndex= 0;
		for(int i =0; i < user.size(); i++ ) {
			if(userToFollow.equals( user.get(i).getCredentials().getUsername())) {
				userToFollowIndex = i;
			}
		}
		for(int i =0; i < user.size(); i++ ) {
			if(credentialsDto.getUsername().equals(user.get(i).getCredentials().getUsername())) {
				followerIndex = i;
			}
			
		}
		List<User> followers = new ArrayList<User>();
		List<User> userToFollow1 = new ArrayList<User>();
		followers.add(user.get(userToFollowIndex));
		userToFollow1.add(user.get(followerIndex));
		if(user.get(userToFollowIndex).getFollowers().equals(userToFollow1) ) {
			throw new NotAuthorizedException("You are already following this user");
		}
		user.get(userToFollowIndex).setFollowers(userToFollow1);

		userRepository.saveAllAndFlush(user);
		
		return null;
	}
	@Override
	public List<User> getFollowing(String username) {
		return userRepository.findByCredentials_UsernameIs(username).getFollowing();
	}

	@Override
	public List<User> getFollowers(String username) {
		return userRepository.findByCredentials_UsernameIs(username).getFollowers();
	} 

	
}
