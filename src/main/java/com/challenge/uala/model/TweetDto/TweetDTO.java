package com.challenge.uala.model.TweetDto;

public class TweetDTO {

    private String content;
    private Long userId; // Este campo se usará para referenciar el usuario asociado al tweet

    // Getters y Setters

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
