package com.berrakaraman.s19_challenge_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Email
    private String email;

    @Size(max = 100)
    private String about;

    @NotNull
    private String password;
}
