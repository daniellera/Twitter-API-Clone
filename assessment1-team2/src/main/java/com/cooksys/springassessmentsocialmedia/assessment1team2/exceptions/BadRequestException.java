package com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4423822449696230707L;

	private String message;
	
}
