package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.HashtagRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.HashtagService;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.UserService;
import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserService userService;
    private final HashtagService hashtagService;

    @Override
    public boolean usernameExists(String username) {
        return userService.getUserByUsername(username) != null;
    }

    @Override
    public boolean hashtagExists(String label) {
        return hashtagService.getHashtagByLabel(label) != null;
    }
}
