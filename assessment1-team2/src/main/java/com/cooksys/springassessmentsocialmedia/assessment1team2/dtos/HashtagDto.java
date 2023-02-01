package com.cooksys.springassessmentsocialmedia.assessment1team2.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto {
	
	private String label;
	
	private Timestamp firstUsed;  
	
	private Timestamp lastUsed;
}
