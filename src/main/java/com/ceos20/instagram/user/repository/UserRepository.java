package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // user 저장
    public void save(User user) {
        em.persist(user);
    }

    // id로 회원 조회
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    // nickname으로 회원 조회
    public User findByNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }
}
