package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private UserService userService;

    @Autowired
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{targetUserId}")
    public void toggleFollow(@PathVariable Long targetUserId) {
        userService.toggleFollow(targetUserId);
    }
}
