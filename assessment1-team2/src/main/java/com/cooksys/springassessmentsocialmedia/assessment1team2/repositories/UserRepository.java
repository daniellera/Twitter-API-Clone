package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findAllByDeletedFalse();

    User findByCredentials_UsernameIs(String username);

	Optional<User> findByCredentialsUsername(String username);

	Optional<User> findOneByCredentials(Credentials credentials);

}
