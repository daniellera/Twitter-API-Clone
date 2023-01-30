package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
