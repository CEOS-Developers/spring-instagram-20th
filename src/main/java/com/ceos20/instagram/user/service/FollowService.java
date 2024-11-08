package com.ceos20.instagram.user.service;

import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.dto.FollowCreateDto;
import com.ceos20.instagram.user.dto.FollowersResponseDto;
import com.ceos20.instagram.user.dto.FollowingsResponseDto;
import com.ceos20.instagram.user.repository.FollowRepository;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createFollow(FollowCreateDto followCreateDto, String nickname, String user){
        final User following = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));
        following.increaseFollowerCount();

        final User accessUser = userRepository.findByNickname(user)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));
        accessUser.increaseFollowingCount();

        final Follow follow = followCreateDto.toEntity(accessUser, following);
        followRepository.save(follow);
    }

    public List<FollowingsResponseDto> getFollowing(String nickname) {
        final User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));

        final List<Follow> followings = followRepository.findByFollowing(user);

        return followings.stream()
                .map(FollowingsResponseDto::from)
                .toList();
    }

    public List<FollowersResponseDto> getFollower(String nickname) {
        final User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));

        final List<Follow> followers = followRepository.findByFollower(user);

        return followers.stream()
                .map(FollowersResponseDto::from)
                .toList();
    }
}
