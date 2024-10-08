package com.ceos20.spring_boot.post.repository;

import com.ceos20.spring_boot.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 특정 게시물에 대한 좋아요 수를 계산하는 메서드
    Long countByPostId(Long postId);
}
