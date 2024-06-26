package com.challenge.uala.repos;

import com.challenge.uala.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    @EntityGraph(attributePaths = {"tweets", "followers"})
    Optional<User> findById(@Param("userId") Long userId);


    @Query("SELECT u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.tweets t " +  // Fetch tweets asociados al usuario
            "WHERE u.id = :userId")
    Optional<User> findByIdWithTweets(@Param("userId") Long userId);
}
