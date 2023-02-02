package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.HashtagDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.dtos.TweetResponseDto;
import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;
import com.cooksys.springassessmentsocialmedia.assessment1team2.exceptions.NotFoundException;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.HashtagMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.mappers.TweetMapper;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.HashtagRepository;
import com.cooksys.springassessmentsocialmedia.assessment1team2.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetMapper tweetMapper;

    @Override
    public Hashtag getHashtagByLabel(String label) {
        return hashtagRepository.findByLabelIs(label);
    }

	@Override
	public List<HashtagDto> getAllTags() {
		return hashtagMapper.entitiesToDto(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTweetByHashtag(String label) {
		Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
		
		if(hashtag.isEmpty()) {
			throw new NotFoundException("No hashtag exists with that label.");
		}
		
		return tweetMapper.entitiesToDtos(hashtag.get().getTweets());
	}
}
