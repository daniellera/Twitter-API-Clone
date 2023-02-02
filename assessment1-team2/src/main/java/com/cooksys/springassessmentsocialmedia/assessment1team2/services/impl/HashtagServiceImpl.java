package com.cooksys.springassessmentsocialmedia.assessment1team2.services.impl;

import com.cooksys.springassessmentsocialmedia.assessment1team2.entities.Hashtag;
import com.cooksys.springassessmentsocialmedia.assessment1team2.repositories.HashtagRepository;
import org.springframework.stereotype.Service;

import com.cooksys.springassessmentsocialmedia.assessment1team2.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public Hashtag getHashtagByLabel(String label) {
        return hashtagRepository.findByLabelIs(label);
    }
}
