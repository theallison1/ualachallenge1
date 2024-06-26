package com.challenge.uala.repos;

import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TweetRepository  extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUserInOrderByCreatedAtDesc(List<UserDtoResponse> users);

    List<Tweet> findByUserId(Long userId);
    @Query("SELECT t.id FROM Tweet t WHERE t.user.id = :userId")
    Set<Long> findTweetIdsByUserId(@Param("userId") Long userId);
}
