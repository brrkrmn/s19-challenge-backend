package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @DisplayName("Can find user from username")
    @Test
    void findByUsername() {
        if (userRepository.findByUsername("testUser").isEmpty()) {
            authenticationService.register(
                    "testUser",
                    "testUser",
                    "testUser@testUser.com",
                    "testUser",
                    "testUser"
            );
        }

        User existingTestUser = userRepository.findByUsername("testUser").orElse(null);
        assertNotNull(existingTestUser);

        userRepository.delete(existingTestUser);
    }
}
