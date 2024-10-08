package com.ceos20.instagram.follow.service;

import com.ceos20.instagram.follow.domain.Follow;
import com.ceos20.instagram.follow.dto.GetFollowerResponse;
import com.ceos20.instagram.follow.dto.GetFollowingResponse;
import com.ceos20.instagram.follow.repository.FollowRepository;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 팔로우 관계 생성
    @Transactional
    public void createFollow(User user, Long friendId) {

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        Follow follow = Follow.toEntity(user, friend);
        followRepository.save(follow);
    }

    // 나를 팔로우하는 사람들 리스트 반환
    public List<GetFollowerResponse> getFollower(User user) {

        List<User> findFollowers = followRepository.findByToUser(user);

        return findFollowers.stream()
                .map(follower -> GetFollowerResponse.fromEntity(follower))
                .collect(Collectors.toList());
    }

    // 내가 팔로우하는 사람들 리스트 반환
    public List<GetFollowingResponse> getFollowing(User user) {

        List<User> findFollowings = followRepository.findByFromUser(user);

        return findFollowings.stream()
                .map(following -> GetFollowingResponse.fromEntity(following))
                .collect(Collectors.toList());
    }

    // 팔로우 관계 삭제
    @Transactional
    public void deleteFollow(User user, Long friendId) {

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        Follow follow = followRepository.findByFromUserAndToUser(user, friend);
        followRepository.delete(follow);
    }
}
