package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.ProfileDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;


@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile profile);

    Profile dtoToEntity(ProfileDto profileDto);

    List<ProfileDto> entitiesToDtos(List<Profile> profileList);

    List<Profile> dtosToEntities(List<ProfileDto> profileDtoList);

}
