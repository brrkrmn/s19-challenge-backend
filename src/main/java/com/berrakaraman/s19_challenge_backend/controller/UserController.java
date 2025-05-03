package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@Positive @PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/{id}/follow")
    public void toggleFollow(@Positive @PathVariable("id") Long id) {
        userService.toggleFollow(id);
    }
}
