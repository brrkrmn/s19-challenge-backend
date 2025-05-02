package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {
    @Autowired
    private final UserService userService;

    @PostMapping("/{tweetId}")
    public void toggleLike(@Positive @PathVariable("tweetId") Long tweetId) {
        userService.toggleLike(tweetId);
    }
}
