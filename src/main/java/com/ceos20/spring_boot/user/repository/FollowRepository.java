package com.ceos20.spring_boot.user.repository;

import com.ceos20.spring_boot.user.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로워와 팔로잉 관계가 존재하는지 확인
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
