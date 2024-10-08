package com.ceos20.instagram.user.service;

import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.dto.UserCreateDto;
import com.ceos20.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public void create(UserCreateDto userCreateDto) {
        if(userRepository.existUserLoginNickname(userCreateDto.getNickname())) {
            throw new IllegalStateException("already exist");
        }

        User user = userCreateDto.toEntity();
        userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(IllegalStateException::new);
    }
}
