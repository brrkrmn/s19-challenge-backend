package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.LoginRequest;
import com.berrakaraman.s19_challenge_backend.dto.SignupRequest;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.service.AuthenticationService;
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
    public User signup(@Validated @RequestBody SignupRequest signupRequest) {
        return authenticationService.register(
                signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getAbout(),
                signupRequest.getPassword());
    }

    @PostMapping("/login")
    public User login(@Validated @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
