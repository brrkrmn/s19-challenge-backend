package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.exception.BadRequestException;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final TweetService tweetService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User credentials are not valid"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User could not be found with id: " + id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User could not be found with username: " + username));
    }

    @Override
    public void toggleFollow(Long targetUserId) {
        User authUser = authenticationService.getAuthUser();
        User targetUser = findById(targetUserId);

        if (authUser.equals(targetUser)) {
            throw new BadRequestException("Self-following is not allowed.");
        }

        if (authUser.getFollowing().contains(targetUser)) {
            authUser.unfollow(targetUser);
        } else {
            authUser.follow(targetUser);
        }

        userRepository.save(authUser);
    }

    public void toggleLike(Long tweetId) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = tweetService.getById(tweetId);

        if (authUser.getLikes().contains(tweet)) {
            authUser.unlike(tweet);
        } else {
            authUser.like(tweet);
        }

        userRepository.save(authUser);
    }
}
