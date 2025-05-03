package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Role;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.exception.ConflictException;
import com.berrakaraman.s19_challenge_backend.exception.NotFoundException;
import com.berrakaraman.s19_challenge_backend.exception.UnauthenticatedException;
import com.berrakaraman.s19_challenge_backend.repository.RoleRepository;
import com.berrakaraman.s19_challenge_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String name, String email, String about, String password) {
        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            throw new ConflictException("Username already taken");
        }

        String encoded = passwordEncoder.encode(password);

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setAbout(about);
        user.setPassword(encoded);
        user.setAuthorities(authorities);

        return userRepository.save(user);
    }

    @Override
    public User authenticate(String username, String password) throws UnauthenticatedException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthenticatedException("Invalid username"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthenticatedException("Invalid password");
        }

        return user;
    }

    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedException("User not authenticated");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Authenticated user is not in database."));
    }
}
