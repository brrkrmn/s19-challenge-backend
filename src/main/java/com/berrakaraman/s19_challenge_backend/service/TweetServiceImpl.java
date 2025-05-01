package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.exception.UnauthorizedException;
import com.berrakaraman.s19_challenge_backend.repository.TweetRepository;
import com.berrakaraman.s19_challenge_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TweetServiceImpl implements TweetService {
    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, AuthenticationService authenticationService, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Tweet> getAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet getById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet could not be found with id: " + id));
    }

    @Transactional
    @Override
    public Tweet create(String content) {
        User authUser = authenticationService.getAuthUser();

        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setUser(authUser);

        authUser.addTweet(tweet);
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(Tweet tweet) {
        return null;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = getById(id);

        if (!tweet.getUser().equals(authUser)) {
            throw new UnauthorizedException("You cannot delete another user's tweet.");
        }

        Set<User> usersWhoLikedTweet =  tweetRepository.getUsersWhoLikedTweet(id);

        for (User user: usersWhoLikedTweet) {
            user.unlike(tweet);
        }

        authUser.removeTweet(tweet);

        userRepository.save(authUser);
        userRepository.saveAll(usersWhoLikedTweet);
        tweetRepository.delete(tweet);
    }
}
