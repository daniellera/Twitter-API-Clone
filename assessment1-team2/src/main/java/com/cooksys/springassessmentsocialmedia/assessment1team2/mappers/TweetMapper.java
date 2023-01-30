package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);
	
	List<TweetResponseDto> entitiesToDtos (List<Tweet> entities);
}
