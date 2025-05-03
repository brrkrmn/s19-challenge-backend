package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.exception.UnauthorizedException;
import com.berrakaraman.s19_challenge_backend.repository.TweetRepository;
import com.berrakaraman.s19_challenge_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {
    @Autowired
    private final TweetRepository tweetRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthenticationService authenticationService;

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

    @Transactional
    @Override
    public Tweet replaceOrCreate(Long id, Tweet tweet) {
        User authUser = authenticationService.getAuthUser();
        Tweet existingTweet = tweetRepository.findById(id).orElse(null);

        if (existingTweet != null) {
            if (!existingTweet.getUser().equals(authUser)) {
                throw new UnauthorizedException("You cannot edit another user's tweet.");
            }

            tweet.setId(id);
            return tweetRepository.save(tweet);
        }

        return create(tweet.getContent());
    }

    @Transactional
    @Override
    public Tweet update(Long id, Tweet tweet) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweetToUpdate = getById(id);

        if (!tweetToUpdate.getUser().equals(authUser)) {
            throw new UnauthorizedException("You cannot edit another user's tweet.");
        }

        if (tweet.getContent() != null) {
            tweetToUpdate.setContent(tweet.getContent());
        }

        return tweetRepository.save(tweetToUpdate);
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
