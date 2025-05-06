package com.berrakaraman.s19_challenge_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @NotNull
    private String password;
}
