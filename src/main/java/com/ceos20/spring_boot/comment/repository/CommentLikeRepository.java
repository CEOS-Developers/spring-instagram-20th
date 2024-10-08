package com.ceos20.spring_boot.comment.repository;

import com.ceos20.spring_boot.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 특정 유저가 특정 댓글을 좋아요 했는지 확인
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);

}
