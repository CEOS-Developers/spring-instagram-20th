package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User Follower);
    List<Follow> findByFollowing(User Following);
}