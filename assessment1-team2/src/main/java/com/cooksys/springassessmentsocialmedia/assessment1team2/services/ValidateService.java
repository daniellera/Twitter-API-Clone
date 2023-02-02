package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

public interface ValidateService {

    boolean usernameExists(String username);

    boolean hashtagExists(String label);
}
