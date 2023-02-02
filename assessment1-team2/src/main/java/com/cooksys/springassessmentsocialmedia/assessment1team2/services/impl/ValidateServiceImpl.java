package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final UserRepository userRepository;
	
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
		
		if(deleted.get().isDeleted()) {
			return true;
			
		} else if (deleted.isEmpty()) {
			return true;
			
		} else {
			return false;			
		}
	}

}
