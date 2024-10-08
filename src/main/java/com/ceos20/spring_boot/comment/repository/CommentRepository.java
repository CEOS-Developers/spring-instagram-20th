package com.ceos20.spring_boot.comment.repository;

import com.ceos20.spring_boot.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 달린 댓글 수 조회
    Long countByPostId(Long postId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.postId = :postId")
    List<Comment> findAllByPostIdWithUser(@Param("postId") Long postId);

}
