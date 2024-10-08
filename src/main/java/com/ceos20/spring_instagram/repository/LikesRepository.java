package com.ceos20.spring_instagram.repository;

import com.ceos20.spring_instagram.domain.Likes;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    // 특정 포스트에 달린 좋아요 조회
    List<Likes> findByPost(Post post);

    // 특정 회원이 누른 좋아요 조회
    List<Likes> findByMember(Member member);
}
