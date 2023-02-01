package com.cooksys.springassessmentsocialmedia.assessment1team2.repositories;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findAllByDeletedFalse();
}
