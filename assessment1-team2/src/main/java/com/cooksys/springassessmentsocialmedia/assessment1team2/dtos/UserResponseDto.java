package com.cooksys.springassessmentsocialmedia.assessment1team2.dtos;

import java.sql.Timestamp;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

	private String username;
	private Profile profile;
	private Timestamp joined;
}
