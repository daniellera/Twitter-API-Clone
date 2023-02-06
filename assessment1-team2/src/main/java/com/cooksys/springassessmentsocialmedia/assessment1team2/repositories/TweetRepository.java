package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import java.util.List;
import java.util.Optional;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

	Optional<Tweet> findByIdAndDeletedFalse(Long id);

	Optional<Tweet> findById(Long id);

	List<Tweet> findAllByAuthorOrderByPostedDesc(User author);

	Tweet findByAuthor(String username);

	List<Tweet> findAllByAuthorAndDeletedFalse(User author);

	List<Tweet> findAllByRepostOf(Tweet tweet);
}
