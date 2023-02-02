package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findAllByDeletedFalse();

    User findByCredentials_UsernameIs(String username);

	Optional<User> findByCredentialsUsername(String username);
	
	

}
