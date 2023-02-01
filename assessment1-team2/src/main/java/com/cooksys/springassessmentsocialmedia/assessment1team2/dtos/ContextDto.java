package com.cooksys.springassessmentsocialmedia.assessment1team2.dtos;

import java.util.ArrayList;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {
	
	private TweetResponseDto target;
	
	private ArrayList<TweetResponseDto> before;
	
	private ArrayList<TweetResponseDto> after;
	
}
