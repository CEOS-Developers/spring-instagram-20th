package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // id로 조회
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    // nickname으로 조회
    public User findByNickname(String nickname) {
        return entityManager.createQuery(
                "SELECT u FROM User u " +
                "WHERE u.nickname = :nickname",
                User.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }


    // 저장
    public void save(User user) {
        entityManager.persist(user);
    }

    // 삭제
    public void delete(User user) {
        entityManager.remove(user);
    }


}
