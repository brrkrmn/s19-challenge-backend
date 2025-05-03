package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Comment;
import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.exception.UnauthorizedException;
import com.berrakaraman.s19_challenge_backend.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final TweetService tweetService;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with " + id + " id could not be found"));
    }

    @Override
    public Comment create(Long tweetId, Comment comment) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = tweetService.getById(tweetId);

        comment.setUser(authUser);
        comment.setTweet(tweet);
        authUser.addComment(comment);
        tweet.addComment(comment);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, Comment comment) {
        User authUser = authenticationService.getAuthUser();

        if (!authUser.equals(comment.getUser())) {
            throw new UnauthorizedException("You are not allowed to edit this comment.");
        }

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = getById(id);
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = comment.getTweet();

        if (!authUser.equals(tweet.getUser()) && !authUser.equals(comment.getUser())) {
            throw new UnauthorizedException("You are not allowed to delete this comment.");
        }

        commentRepository.delete(comment);
        authUser.removeComment(comment);
        tweet.removeComment(comment);
    }
}
