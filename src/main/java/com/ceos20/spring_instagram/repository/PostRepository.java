package com.ceos20.spring_instagram.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 회원의 포스트를 조회하는 메서드
    List<Post> findByMember(Member member);

    // N+1 문제를 해결하기 위해 EntityGraph로 Member를 한 번에 로드
    @EntityGraph(attributePaths = "member")
    List<Post> findAll();  // 모든 포스트와 연관된 멤버를 함께 조회
}
