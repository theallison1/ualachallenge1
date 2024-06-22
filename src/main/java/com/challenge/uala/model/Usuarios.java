package com.challenge.uala.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuarios {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;
    @NotNull
    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;
    @NotNull
    @Column(name = "EMAIL")
    private String email;
    @NotNull
    @Column(name = "PHONE")
    private String phone;

    @OneToMany(mappedBy = "usario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tweet> tweets = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<Usuarios> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<Usuarios> following = new HashSet<>();



}
