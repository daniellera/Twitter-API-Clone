package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.UserResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
	
	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User entity);

	List<UserResponseDto> entitiesToDtos(List<User> entities);

	User dtoToEntity(UserRequestDto userRequestDto);

}
