package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

public interface ValidateService {

	boolean available(String username);

	boolean deleted(String username);

    boolean usernameExists(String username);

    boolean hashtagExists(String label);

    boolean isUsernameAvailable(String username);
}
