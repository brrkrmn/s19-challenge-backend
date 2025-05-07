package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.UserResponse;
import com.berrakaraman.s19_challenge_backend.service.UserService;
import com.berrakaraman.s19_challenge_backend.util.UserMapper;
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
    public List<UserResponse> getAll() {
        return userService.getAll().stream().map(UserMapper::toUserResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@Positive @PathVariable("id") Long id) {
        return UserMapper.toUserResponse(userService.getById(id));
    }

    @PostMapping("/{id}/follow")
    public void toggleFollow(@Positive @PathVariable("id") Long id) {
        userService.toggleFollow(id);
    }
}
