package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.HashtagResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagResponseDto entityToDto(Hashtag entity);
	
	List<HashtagResponseDto> entitiesToDto (List<Hashtag> entities);

}
