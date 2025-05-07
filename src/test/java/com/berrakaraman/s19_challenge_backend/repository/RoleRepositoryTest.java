package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setAuthority("TEST");
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        Role role = roleRepository.findByAuthority("TEST").orElse(null);
        if (role != null ) roleRepository.delete(role);
    }

    @DisplayName("Can find role from authority")
    @Test
    void findByAuthority() {
        Role foundRole = roleRepository.findByAuthority("TEST").orElse(null);
        assertNotNull(foundRole);
    }

    @DisplayName("Should contain 'user' role by default")
    @Test
    void containsUserRole() {
        Role userRole = roleRepository.findByAuthority("USER").orElse(null);
        assertNotNull(userRole);
    }

    @DisplayName("Should contain 'admin' role by default")
    @Test
    void containsAdminRole() {
        Role adminRole = roleRepository.findByAuthority("ADMIN").orElse(null);
        assertNotNull(adminRole);
    }
}
