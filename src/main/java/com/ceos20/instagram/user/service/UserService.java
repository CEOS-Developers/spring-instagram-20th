package com.ceos20.instagram.user.service;

import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.auth.service.RedisService;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.dto.UserPageResponseDto;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;


    private final RedisService redisService;
//
//    @Transactional
//    public void create(UserCreateDto userCreateDto) {
//        if(userRepository.existUserLoginNickname(userCreateDto.getNickname())) {
//            throw new IllegalStateException("already exist");
//        }
//
//        User user = userCreateDto.toEntity();
//        userRepository.save(user);
//    }
//
//    public User getUser(Long userId) {
//        return userRepository.findUserById(userId)
//                .orElseThrow(IllegalStateException::new);
//    }

    public UserPageResponseDto getUserPage(String nickname){

        final User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));

        final List<Post> posts = postRepository.findByWriter_Id(user.getId());
        return UserPageResponseDto.of(user, posts);
    }


}
