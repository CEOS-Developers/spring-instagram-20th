package com.ceos20.instagram.follow.repository;

import com.ceos20.instagram.follow.domain.Follow;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;

    // follow 저장
    public void save(Follow follow) {
        em.persist(follow);
    }

}
