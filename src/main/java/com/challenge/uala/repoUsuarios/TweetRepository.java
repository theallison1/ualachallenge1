package com.challenge.uala.repoUsuarios;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TweetRepository  extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUserOrderByCreatedAtDesc(Usuarios user);
}
