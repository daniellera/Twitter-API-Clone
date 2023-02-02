package com.cooksys.springassessmentsocialmedia.assessment1team2.services;

import java.util.List;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.HashtagDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;

public interface HashtagService {

    Hashtag getHashtagByLabel(String label);

	List<HashtagDto> getAllTags();

	List<TweetResponseDto> getTweetByHashtag(String label);
}
