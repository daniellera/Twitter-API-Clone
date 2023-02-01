package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
	
	UserResponseDto entityToDto(User entity);

	List<UserResponseDto> entitiesToDtos(List<User> entities);
}
