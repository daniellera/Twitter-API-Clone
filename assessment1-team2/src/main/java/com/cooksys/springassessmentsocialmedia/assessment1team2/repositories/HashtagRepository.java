package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByLabelIs(String label);

	Optional<Hashtag> findByLabel(String label);
}
