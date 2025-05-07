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

    @Override
    @Transactional
    public Tweet create(String content) {
        User authUser = authenticationService.getAuthUser();

        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setUser(authUser);

        authUser.addTweet(tweet);
        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional
    public Tweet replaceOrCreate(Long id, String content) {
        User authUser = authenticationService.getAuthUser();
        Tweet existingTweet = tweetRepository.findById(id).orElse(null);

        if (existingTweet != null) {
            if (!existingTweet.getUser().equals(authUser)) {
                throw new UnauthorizedException("You cannot edit another user's tweet.");
            }

            existingTweet.setId(id);
            existingTweet.setContent(content);
            return tweetRepository.save(existingTweet);
        }

        return create(content);
    }

    @Override
    @Transactional
    public Tweet update(Long id, String content) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweetToUpdate = getById(id);

        if (!tweetToUpdate.getUser().equals(authUser)) {
            throw new UnauthorizedException("You cannot edit another user's tweet.");
        }

        if (content != null) {
            tweetToUpdate.setContent(content);
        }

        return tweetRepository.save(tweetToUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = getById(id);

        if (!tweet.getUser().equals(authUser)) {
            throw new UnauthorizedException("You cannot delete another user's tweet.");
        }

        Set<User> usersWhoLiked =  tweetRepository.getUsersWhoLiked(id);
        for (User user: usersWhoLiked) {
            user.removeLike(tweet);
        }

        Set<User> usersWhoRetweeted =  tweetRepository.getUsersWhoRetweeted(id);
        for (User user: usersWhoRetweeted) {
            user.removeRetweet(tweet);
        }

        authUser.removeTweet(tweet);

        userRepository.save(authUser);
        userRepository.saveAll(usersWhoLiked);
        userRepository.saveAll(usersWhoRetweeted);
        tweetRepository.delete(tweet);
    }

    @Override
    @Transactional
    public Tweet toggleRetweet(Long id) {
        Tweet tweet = getById(id);
        User authUser = authenticationService.getAuthUser();

        if (authUser.getRetweets().contains(tweet)) {
            authUser.removeRetweet(tweet);
            tweet.removeRetweetBy(authUser);
        } else {
            authUser.addRetweet(tweet);
            tweet.addRetweetBy(authUser);
        }

        userRepository.save(authUser);
        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional
    public Tweet toggleLike(Long id) {
        Tweet tweet = getById(id);
        User authUser = authenticationService.getAuthUser();

        if (authUser.getLikes().contains(tweet)) {
            authUser.removeLike(tweet);
            tweet.removeLikeBy(authUser);
        } else {
            authUser.addLike(tweet);
            tweet.addLikeBy(authUser);
        }

        userRepository.save(authUser);
        return tweetRepository.save(tweet);
    }
}
