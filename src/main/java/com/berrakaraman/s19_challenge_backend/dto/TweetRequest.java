package com.berrakaraman.s19_challenge_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequest {
    @NotNull
    @Size(min = 3, max = 280)
    private String content;
}
