package com.berrakaraman.s19_challenge_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignupRequest {
    private String username;
    private String name;
    private String email;
    private String about;
    private String password;
}
