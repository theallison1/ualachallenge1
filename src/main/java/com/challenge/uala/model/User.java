    package com.challenge.uala.model;


    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    import java.util.HashSet;
    import java.util.List;
    import java.util.Objects;
    import java.util.Set;


    @Getter
    @Setter
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(tweets, user.tweets) && Objects.equals(followers, user.followers);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public @NotNull String getUsername() {
            return username;
        }

        public void setUsername(@NotNull String username) {
            this.username = username;
        }

        public List<Tweet> getTweets() {
            return tweets;
        }

        public void setTweets(List<Tweet> tweets) {
            this.tweets = tweets;
        }

        public Set<User> getFollowers() {
            return followers;
        }

        public void setFollowers(Set<User> followers) {
            this.followers = followers;
        }

        public Set<User> getFollowing() {
            return following;
        }

        public void setFollowing(Set<User> following) {
            this.following = following;
        }
    }
