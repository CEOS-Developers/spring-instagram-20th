package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class FollowRepositoryTest {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("user1을 팔로워 목록 조회")
    void findFollowingUsersTest() {
        // given
        User user1 = User.builder()
                .nickname("user1")
                .password("1234")
                .build();
        entityManager.persist(user1);

        User user2 = User.builder()
                .nickname("user2")
                .password("1234")
                .build();;
        entityManager.persist(user2);

        User user3 = User.builder()
                .nickname("user3")
                .password("1234")
                .build();;
        entityManager.persist(user3);

        User user4 = User.builder()
                .nickname("user4")
                .password("1234")
                .build();;
        entityManager.persist(user4);

        Follow target1 = Follow.builder()
                .followerId(user2)
                .followingId(user1)
                .build();

        Follow target2 = Follow.builder()
                .followerId(user3)
                .followingId(user1)
                .build();

        Follow Nontarget = Follow.builder()
                .followerId(user1)
                .followingId(user4)
                .build();

        // when
        followRepository.save(target1);
        followRepository.save(target2);
        followRepository.save(Nontarget);

        List<User> followingList = followRepository.findFollowerUsers(user1.getId());

        //then
        assertEquals(2, followingList.size());
    }
}
