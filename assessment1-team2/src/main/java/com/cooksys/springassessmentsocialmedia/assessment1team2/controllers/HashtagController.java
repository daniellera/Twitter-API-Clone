package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.HashtagDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	private final HashtagService hashtagService;

	@GetMapping
	public List<HashtagDto> getAllTags(){
		return hashtagService.getAllTags();
	}
	
	@GetMapping("/@{label}")
	public List<TweetResponseDto> getTweetByHashtag(@PathVariable String label){
		return hashtagService.getTweetByHashtag(label);
	}
}
