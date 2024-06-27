    package com.challenge.uala.model;


    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.HashSet;
    import java.util.List;
    import java.util.Objects;
    import java.util.Set;


    @Data
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "tbl_user", indexes = @Index(name = "idx_username", columnList = "username"))
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        private String username;


        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Tweet> tweets;

        @ManyToMany
        @JoinTable(
                name = "user_followers",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "follower_id")
        )
        private Set<User> followers = new HashSet<>();

        @ManyToMany(mappedBy = "followers")
        private Set<User> following = new HashSet<>();

        public void addFollower(User follower) {
            followers.add(follower);
            follower.getFollowing().add(this);
        }

        public void removeFollower(User follower) {
            followers.remove(follower);
            follower.getFollowing().remove(this);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(tweets, user.tweets) && Objects.equals(followers, user.followers);
        }


    }
