package com.berrakaraman.s19_challenge_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "comment")
@ToString(onlyExplicitlyIncluded = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @NotNull
    @Size(min = 3, max = 280)
    private String content;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @NotNull
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}
