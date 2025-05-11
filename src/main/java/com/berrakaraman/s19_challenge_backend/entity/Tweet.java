package com.berrakaraman.s19_challenge_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tweet")
@ToString(onlyExplicitlyIncluded = true)
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @NotNull
    @Size(min = 3, max = 280)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToMany(mappedBy = "likes")
    private Set<User> likedBy = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "retweets")
    private Set<User> retweetedBy = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void addRetweetBy(User user) {
        retweetedBy.add(user);
    }

    public void removeRetweetBy(User user) {
        retweetedBy.remove(user);
    }

    public void addLikeBy(User user) {
        likedBy.add(user);
    }

    public void removeLikeBy(User user) {
        likedBy.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(id, tweet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
