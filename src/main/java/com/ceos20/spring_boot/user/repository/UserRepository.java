package com.ceos20.spring_boot.user.repository;

import com.ceos20.spring_boot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 특정 이름을 가진 사용자가 있는지 확인
    boolean existsByName(String name);

    // 특정 ID로 사용자 조회
    Optional<User> findById(Long userId);
}
