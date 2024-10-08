package com.ceos20.instagram.user.service;

import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.dto.GetUserInfoResponse;
import com.ceos20.instagram.user.dto.SaveUserRequest;
import com.ceos20.instagram.user.dto.UpdateUserInfoRequest;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // user 저장
    @Transactional
    public Long saveUser(SaveUserRequest saveUserRequest) {

        User user = saveUserRequest.toEntity();
        return userRepository.save(user).getId();
    }

    // user 정보 수정
    @Transactional
    public Long updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest, User user) {

        user.updateInfo(
                updateUserInfoRequest.getUsername(),
                updateUserInfoRequest.getNickname(),
                updateUserInfoRequest.getPassword(),
                updateUserInfoRequest.getEmail(),
                updateUserInfoRequest.getImageUrl()
                );
        userRepository.save(user);   // JPA의 변경감지 덕분에 생략 가능하다고 함

        return user.getId();
    }

    // user 정보 조회
    public GetUserInfoResponse getUserInfoById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 user 입니다."));

        return GetUserInfoResponse.fromEntity(user);
    }
}
