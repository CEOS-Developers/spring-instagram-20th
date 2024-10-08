package com.ceos20.spring_boot.user.service;

import com.ceos20.spring_boot.user.domain.Follow;
import com.ceos20.spring_boot.user.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // 팔로우 추가
    @Transactional
    public Follow followUser(Follow follow) {
        // 중복 팔로우 방지 등의 비즈니스 로직 추가 가능
        return followRepository.save(follow);
    }

    // 팔로우 취소
    @Transactional
    public void unfollowUser(Long followId) {
        followRepository.deleteById(followId);
    }

    // 팔로우 조회
    @Transactional(readOnly = true)
    public Follow getFollowById(Long followId) {
        return followRepository.findById(followId)
                .orElseThrow(() -> new RuntimeException("Follow not found with id: " + followId));
    }
}
