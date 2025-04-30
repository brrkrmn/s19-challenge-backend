package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet could not be found with id: " + id));
    }
}
