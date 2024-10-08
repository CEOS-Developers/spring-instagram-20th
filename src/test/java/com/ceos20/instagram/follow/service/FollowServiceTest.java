package com.ceos20.instagram.follow.service;

import com.ceos20.instagram.follow.dto.GetFollowerResponse;
import com.ceos20.instagram.follow.dto.GetFollowingResponse;
import com.ceos20.instagram.follow.repository.FollowRepository;
import com.ceos20.instagram.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private FollowService followService;

    private User user;
    private User follower1;
    private User follower2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .nickname("user")
                .password("password")
                .build();

        follower1 = User.builder()
                .id(2L)
                .nickname("follower1")
                .password("password")
                .build();

        follower2 = User.builder()
                .id(3L)
                .nickname("follower2")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("나를 팔로우하는 사람들 반환 테스트")
    void getFollowerTest() {
        // given
        List<User> followers = Arrays.asList(follower1, follower2);
        when(followRepository.findByToUser(user)).thenReturn(followers);

        // when
        List<GetFollowerResponse> responses = followService.getFollower(user);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }

    @Test
    @DisplayName("내가 팔로우하는 사람들 리스트 반환")
    void getFollowingTest() {
        // given
        List<User> following = Arrays.asList(follower1, follower2);
        when(followRepository.findByFromUser(user)).thenReturn(following);

        // when
        List<GetFollowingResponse> responses = followService.getFollowing(user);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }
}
