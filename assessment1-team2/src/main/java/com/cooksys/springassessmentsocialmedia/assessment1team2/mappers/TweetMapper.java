package com.cooksys.springassessmentsocialmedia.assessment1team2.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetRequestDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);
	
	List<TweetResponseDto> entitiesToDtos (List<Tweet> entities);

	Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);


	Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
