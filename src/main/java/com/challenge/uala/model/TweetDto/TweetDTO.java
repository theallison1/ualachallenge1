package com.challenge.uala.model.TweetDto;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class TweetDTO {

    @Size(max = 280)
    private String content;
    private Long userId; // Este campo se usará para referenciar el usuario asociado al tweet

    private LocalDateTime createdAt;

    // Getters y Setters


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
