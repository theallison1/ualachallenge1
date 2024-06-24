package com.challenge.uala.model.DtoUsuarios;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private Set<Long> tweetIds;
    private Set<Long> followerIds;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Long> getTweetIds() {
        return tweetIds;
    }

    public void setTweetIds(Set<Long> tweetIds) {
        this.tweetIds = tweetIds;
    }

    public Set<Long> getFollowerIds() {
        return followerIds;
    }

    public void setFollowerIds(Set<Long> followerIds) {
        this.followerIds = followerIds;
    }
}
