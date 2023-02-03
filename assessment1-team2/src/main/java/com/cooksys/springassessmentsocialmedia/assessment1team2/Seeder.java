package com.cooksys.springassessmentsocialmedia.assessment1team2;

import java.util.Arrays;

import org.apache.catalina.startup.CredentialHandlerRuleSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.TweetRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
	private UserRepository userRepository;
	private TweetRepository tweetRepository;

@Override
public void run(String... args) throws Exception {
	//Populate the DB to do 
//	Tweet tweet1 = new Tweet();
//	tweet1.setContent("this is my first tweet!");
//	Tweet tweet2 = new Tweet();
//	tweet2.setContent("this is my second tweet!");
//	tweetRepository.saveAll(Arrays.asList(new Tweet[] { tweet1, tweet2 }));;
	User user1 = new User();
	Credentials creds = new Credentials();
	Profile profile1 = new Profile();
	profile1.setEmail("mboren@cooksys.com");
	profile1.setFirstName("Michael");
	profile1.setLastName("Boren");
	profile1.setPhoneNumber("(901)907-7909");
	creds.setUsername("mboren");
	creds.setPassword("password");
	user1.setCredentials(creds);
	user1.setProfile(profile1);
	User user2 = new User();
	Credentials creds2 = new Credentials();
	Profile profile2 = new Profile();
	profile2.setEmail("jacksemail@gmail.com");
	profile2.setFirstName("Will");
	profile2.setLastName("Martla");
	profile2.setPhoneNumber("123-123-1234");
	creds2.setUsername("wmarttala");
	creds2.setPassword("password");
	user2.setCredentials(creds2);
	user2.setProfile(profile2);
	
	userRepository.saveAll(Arrays.asList(new User[] { user1,user2 }));
	
	
}
}
