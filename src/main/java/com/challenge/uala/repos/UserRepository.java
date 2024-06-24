package com.challenge.uala.repos;

import com.challenge.uala.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    @EntityGraph(attributePaths = {"tweets", "followers"})
    Optional<User> findById(Long id);
}
