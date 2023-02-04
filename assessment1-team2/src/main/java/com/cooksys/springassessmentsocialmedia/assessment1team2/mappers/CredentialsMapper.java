package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.CredentialsDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Credentials;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials credentials);

    Credentials dtoToEntity(CredentialsDto credentialsDto);

    List<CredentialsDto> entitiesToDtos(List<Credentials> credentialsList);

    List<Credentials> dtosToEntities(List<CredentialsDto> credentialsDtoList);

    
    
}
