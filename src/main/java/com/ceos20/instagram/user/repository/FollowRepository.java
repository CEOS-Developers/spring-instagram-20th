package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // id로 조회
    public Optional<Follow> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Follow.class, id));
    }

    // following 목록 조회
    public List<User> findFollowingUsers(Long userId) {
        return entityManager.createQuery(
                "SELECT f.followingId FROM Follow f " +
                "WHERE f.followerId.id = :userId",
                User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // follower 목록 조회
    public List<User> findFollowerUsers(Long userId) {
        return entityManager.createQuery(
                "SELECT f.followerId FROM Follow f " +
                "WHERE f.followingId.id = :userId",
                User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 저장
    public void save(Follow follow) {
        entityManager.persist(follow);
    }

    // 삭제
    public void delete(Follow follow) {
        entityManager.remove(follow);
    }
}
