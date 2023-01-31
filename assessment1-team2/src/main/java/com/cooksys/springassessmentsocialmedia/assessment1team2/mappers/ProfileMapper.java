package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile profile);

    Profile dtoToEntity(ProfileDto profileDto);

    List<ProfileDto> entitiesToDtos(List<Profile> profileList);

    List<Profile> dtosToEntities(List<ProfileDto> profileDtoList);

}
