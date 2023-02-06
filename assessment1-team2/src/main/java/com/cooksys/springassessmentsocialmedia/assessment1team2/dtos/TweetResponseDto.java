package com.cooksys.springassessmentsocialmedia.assessment1team2.dtos;

import java.sql.Timestamp;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Tweet;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {

	private Long id;
	private User author;
	private Timestamp posted;
	private String content;
	private Tweet inReplyTo;
	private Tweet repostOf;
	
}
