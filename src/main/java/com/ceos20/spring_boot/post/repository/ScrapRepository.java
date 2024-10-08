package com.ceos20.spring_boot.post.repository;

import com.ceos20.spring_boot.post.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    // 특정 유저의 모든 스크랩 조회
    List<Scrap> findByUserId(Long userId);
}
