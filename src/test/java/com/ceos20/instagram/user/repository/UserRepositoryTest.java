package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("nickname으로 유저 조회")
    void findByNicknameTest() {
        // given
        User user = User.builder()
                .nickname("010709min")
                .name("유지민")
                .password("1234")
                .registedAt(LocalDateTime.now())
                .build();

        // when
        userRepository.save(user);
        User findUser = userRepository.findUserByNickname("010709min");

        //then
        assertEquals(user.getNickname(), findUser.getNickname());
    }

    @Test
    @DisplayName("id로 유저 조회")
    void findByIdTest() {
        // given
        User user = User.builder()
                .nickname("010709min")
                .name("유지민")
                .password("1234")
                .registedAt(LocalDateTime.now())
                .build();

        // when
        userRepository.save(user);
        Optional<User> findUser = userRepository.findUserById(user.getId());

        // then
        assertEquals(user.getId(), findUser);
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        // given
        User user = User.builder()
                .nickname("010709min")
                .name("유지민")
                .password("1234")
                .registedAt(LocalDateTime.now())
                .build();

        // when
        userRepository.save(user);
        // userRepository.
    }






}
