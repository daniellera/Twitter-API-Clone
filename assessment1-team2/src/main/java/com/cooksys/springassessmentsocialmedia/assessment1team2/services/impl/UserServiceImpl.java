package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.*;
import org.springframework.stereotype.Service;

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
		if(checkedUser == null || checkedUser.getCredentials().getUsername().isEmpty()) {
			throw new NotAuthorizedException("Invalid credentials, please try again.");
		} else if (checkedUser.isDeleted()) {
			throw new NotAuthorizedException("User is already deleted.");
		}
		return checkedUser;
	}

	private Profile setupProfile(Profile profile, ProfileDto profileDto){
		if (profileDto.getEmail() != null) profile.setEmail(profileDto.getEmail());
		if (profileDto.getPhone() != null) profile.setPhone(profileDto.getPhone());
		if (profileDto.getFirstName() != null) profile.setFirstName(profileDto.getFirstName());
		if (profileDto.getLastName() != null) profile.setLastName(profileDto.getLastName());
		return profile;
	}
	
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		
		User userToCreate = userMapper.dtoToEntity(userRequestDto);
		
		if(userToCreate.getCredentials() == null){
			throw new BadRequestException("Please enter username, password, and email.");
		}
		if(userToCreate.getCredentials().getUsername() == null) {
			throw new BadRequestException("Please enter a valid username.");
		}
		if(userToCreate.getCredentials().getPassword() == null) {
			throw new BadRequestException("Please enter a valid password.");
		}
		if(userToCreate.getProfile() == null) {
			throw new BadRequestException("Please fill out your profile with a valid email");
		}
		if(userToCreate.getProfile().getEmail() == null){
			throw new BadRequestException("A valid email is required.");
		}
		
		if(userToCreate.getCredentials() != null && userToCreate.getProfile() != null) {
			Optional<User> user = userRepository.findByCredentialsUsername(userRequestDto.getCredentials().getUsername());
			
				if(user.isPresent() && user.get().isDeleted() == false) {
					throw new BadRequestException("Username is already taken. Please choose another and try again.");
				}
				
				if(user.isPresent() && user.get().isDeleted()) {
					String username = userRequestDto.getCredentials().getUsername();
					String email = userRequestDto.getProfile().getEmail();
					String password = userRequestDto.getCredentials().getPassword();
					user.get().setDeleted(false);
					user.get().setCredentials(userToCreate.getCredentials());
					user.get().setProfile(userToCreate.getProfile());
					user.get().getCredentials().setUsername(username);
					user.get().getCredentials().setPassword(password);
					user.get().getProfile().setEmail(email);
					Timestamp joined = user.get().getJoined();
					user.get().setJoined(joined);
					
					return userMapper.entityToDto(userRepository.saveAndFlush(user.get()));
				}
				if(user.isEmpty()) {
					userToCreate.getCredentials().setUsername(userRequestDto.getCredentials().getUsername());
					userToCreate.getCredentials().setPassword(userRequestDto.getCredentials().getPassword());
					userToCreate.getProfile().setEmail(userRequestDto.getProfile().getEmail());
					userToCreate.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
					userToCreate.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
					return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
				}
			}
		
		userToCreate.getCredentials().setUsername(userRequestDto.getCredentials().getUsername());
		userToCreate.getCredentials().setPassword(userRequestDto.getCredentials().getPassword());
		userToCreate.getProfile().setEmail(userRequestDto.getProfile().getEmail());
		userToCreate.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
		userToCreate.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
		return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
	}
//		if(user.isEmpty()) {
//			user.get().setDeleted(false);
//			user.get().setCredentials(userToCreate.getCredentials());
//			user.get().getCredentials().setUsername(username);
//			user.get().getCredentials().setPassword(password);
//			user.get().getProfile().setEmail(email);
//			Timestamp joined = user.get().getJoined();
//			user.get().setJoined(joined);
//		}

//		boolean available = validateService.available(userToCreate.getCredentials().getUsername());
		
//		boolean deleted = validateService.deleted(userToCreate.getCredentials().getUsername());

//		if(!available) {
//			throw new BadRequestException("Username is already taken. Please choose another and try again.");
//		} 
//			else if(deleted == true) {
//			User setDeletedUser = userRepository.findByCredentials_UsernameIs(userToCreate.getCredentials().getUsername());
//			setDeletedUser.setDeleted(false);
//			setDeletedUser.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
//			setDeletedUser.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
//			return userMapper.entityToDto(userRepository.saveAndFlush(setDeletedUser));
//		}
		
		
//		if(user.isEmpty()) {
//			user.get().setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
//			user.get().getCredentials().setUsername(username);
//			user.get().getCredentials().setPassword(password);
//			user.get().setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
//			user.get().getProfile().setEmail(email);
//			return userMapper.entityToDto(userRepository.saveAndFlush(user.get()));
//		}
		
//		userToCreate.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
//		userToCreate.getCredentials().setUsername(username);
//		userToCreate.getCredentials().setPassword(password);
//		userToCreate.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
//		userToCreate.getProfile().setEmail(email);
//		return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
//	}
	
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
	public List<TweetResponseDto> getFeed(String username) {
		User user = userRepository.findByCredentials_UsernameIs(username);
		if (user == null) throw new BadRequestException("No user found for username @" + username);
		return tweetService.getFeed(user, user.getFollowing());
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
		if (!validateService.usernameExists(username)) throw new BadRequestException("No such user");
        User userToUpdate = userRepository.findByCredentials_UsernameIs(username);
		Timestamp joined = userToUpdate.getJoined();
		if (userToUpdate.isDeleted()) throw new BadRequestException("No such user");
        Credentials credentials = credentialsMapper.dtoToEntity(userRequestDto.getCredentials());
        if (!userToUpdate.getCredentials().equals(credentials))
            throw new NotAuthorizedException("You are not authorized to make this change");
		if (userRequestDto.getProfile() == null)
			throw new BadRequestException("No changes provided");
        userToUpdate.setProfile(setupProfile(userToUpdate.getProfile(), userRequestDto.getProfile()));
        userRepository.saveAndFlush(userToUpdate);
//		userRepository.setJoined(userToUpdate.getId(), joined);
		return userMapper.entityToDto(userRepository.findByCredentials_UsernameIs(username));
    }
    @Override
	public CredentialsDto followUser(String userToFollow, CredentialsDto credentialsDto) {
		if (userRepository.findByCredentials_UsernameIs(userToFollow) == null ||
				userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername()) == null ||
				credentialsDto.getPassword() == null
		) {
			throw new NotFoundException("One of the users given is not created"); 
		}
		User userToFollowObj = userRepository.findByCredentials_UsernameIs(userToFollow);
		User followersObj = userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername());
		List<User> followers = new ArrayList<User>();
		List<User> userToFollow1 = new ArrayList<User>();
		followers.add(userToFollowObj);
		userToFollow1.add(followersObj);
		if(userToFollowObj.getFollowers().equals(userToFollow1) ) {
			throw new NotAuthorizedException("You are already following this user");
		}
		userToFollowObj.setFollowers(userToFollow1);

		userRepository.saveAndFlush(userToFollowObj);
		
		return null;
	}
	@Override
	public List<UserResponseDto> getFollowing(String username) {
		if (userRepository.findByCredentials_UsernameIs(username) == null) {
			throw new NotFoundException("No user exists with username " + username); 
		}
		return userMapper.entitiesToDtos(userRepository.findByCredentials_UsernameIs(username).getFollowing());
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		if (userRepository.findByCredentials_UsernameIs(username) == null) {
			throw new NotFoundException("No user exists with username " + username); 
		}
		return userMapper.entitiesToDtos(userRepository.findByCredentials_UsernameIs(username).getFollowers());
	}

	@Override
	public CredentialsDto unfollowUser(String userToFollow, CredentialsDto credentialsDto) {
		if (userRepository.findByCredentials_UsernameIs(userToFollow) == null || userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername()) == null ) {
			throw new NotFoundException("One of the users given is not created"); 
		}
		User userToUnfollowObj = userRepository.findByCredentials_UsernameIs(userToFollow);
		User followersObj = userRepository.findByCredentials_UsernameIs(credentialsDto.getUsername());
		List<User> userToUnfollow = new ArrayList<User>();
		userToUnfollow = followersObj.getFollowing();
		int indexOfUserToUnfollow = 0;
		//Remove user from list then update it 
		for(int i = 0; i < userToUnfollow.size(); i++) {
			indexOfUserToUnfollow = i;
			if(userToUnfollow.get(indexOfUserToUnfollow) == userToUnfollowObj) {
				userToUnfollow.remove(indexOfUserToUnfollow);
				break;
			}
		}
		userRepository.saveAndFlush(userToUnfollowObj);
		return null;
	}
	
}
