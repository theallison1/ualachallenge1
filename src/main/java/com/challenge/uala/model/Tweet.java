package com.challenge.uala.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
@Data
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
}
