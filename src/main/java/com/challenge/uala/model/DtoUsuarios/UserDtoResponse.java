package com.challenge.uala.model.DtoUsuarios;


import lombok.Data;

import java.util.Set;
@Data
public class UserDtoResponse {
    private Long id;
    private String username;
    private Set<Long> tweetIds;
    private Set<UserDtoResponse> followers;  // Representación completa de los seguidores
    private Set<Long> followerIds;           // Solo IDs de los seguidores

    // Constructor vacío
    public UserDtoResponse() {
    }

    // Getters y setters para todos los campos

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

    public Set<UserDtoResponse> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserDtoResponse> followers) {
        this.followers = followers;
    }

    public Set<Long> getFollowerIds() {
        return followerIds;
    }

    public void setFollowerIds(Set<Long> followerIds) {
        this.followerIds = followerIds;
    }
}