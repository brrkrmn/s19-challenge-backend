package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.User;

public interface AuthenticationService {
    User register(String username, String name, String email, String about, String password);
}
