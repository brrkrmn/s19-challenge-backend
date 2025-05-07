package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    User getById(Long id);
    User getByUsername(String username);
    void toggleFollow(Long targetUserId);
}
