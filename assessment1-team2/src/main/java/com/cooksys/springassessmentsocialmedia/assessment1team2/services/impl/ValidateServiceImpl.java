package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.HashtagService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final UserRepository userRepository;
	private final HashtagService hashtagService;
	
	@Override
	public boolean available(String username) {
		
		Optional<User> usernameAvailable = userRepository.findByCredentialsUsername(username);
		
		if(usernameAvailable.isEmpty() || usernameAvailable.get().isDeleted()) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean deleted(String username) {
		Optional<User> deleted = userRepository.findByCredentialsUsername(username);
		
		if(deleted.isEmpty()) {
			return false;
			
		} else {
			return true;			
		}
	}

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByCredentials_UsernameIs(username) != null;
    }

    @Override
    public boolean hashtagExists(String label) {
        return hashtagService.getHashtagByLabel(label) != null;
    }

	@Override
	public boolean isUsernameAvailable(String username) {
		return !usernameExists(username);
	}
}
