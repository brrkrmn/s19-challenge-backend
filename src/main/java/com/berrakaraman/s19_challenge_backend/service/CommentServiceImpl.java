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
    public Comment create(Long tweetId, String content) {
        User authUser = authenticationService.getAuthUser();
        Tweet tweet = tweetService.getById(tweetId);

        Comment comment = new Comment();
        comment.setUser(authUser);
        comment.setTweet(tweet);
        comment.setContent(content);
        authUser.addComment(comment);
        tweet.addComment(comment);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, String content) {
        User authUser = authenticationService.getAuthUser();
        Comment comment = getById(id);

        if (!authUser.equals(comment.getUser())) {
            throw new UnauthorizedException("You are not allowed to edit this comment.");
        }

        comment.setContent(content);
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
