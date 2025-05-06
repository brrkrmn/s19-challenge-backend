package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.LoginRequest;
import com.berrakaraman.s19_challenge_backend.dto.SignupRequest;
import com.berrakaraman.s19_challenge_backend.dto.UserResponse;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.service.AuthenticationService;
import com.berrakaraman.s19_challenge_backend.util.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public UserResponse signup(@Validated @RequestBody SignupRequest signupRequest) {
        User user = authenticationService.register(
                signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getAbout(),
                signupRequest.getPassword());

        return UserMapper.toUserResponse(user);
    }

    @PostMapping("/login")
    public UserResponse login(@Validated @RequestBody LoginRequest loginRequest) {
        User user = authenticationService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword());
        return UserMapper.toUserResponse(user);
    }
}
