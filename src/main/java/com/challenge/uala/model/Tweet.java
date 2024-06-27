package com.challenge.uala.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
@Getter
@Setter
public class Tweet {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    // Getters y Setters omitidos para brevedad

 @PrePersist
 protected void onCreate() {
  this.createdAt = LocalDateTime.now();
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public @NotNull String getContent() {
  return content;
 }

 public void setContent(@NotNull String content) {
  this.content = content;
 }

 public LocalDateTime getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
 }

 public User getUser() {
  return user;
 }

 public void setUser(User user) {
  this.user = user;
 }
}
