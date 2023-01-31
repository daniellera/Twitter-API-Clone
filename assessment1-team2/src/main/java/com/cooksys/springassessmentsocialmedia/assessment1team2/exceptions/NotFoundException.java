package com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5014034308240750153L;

	private String message;
	
}
