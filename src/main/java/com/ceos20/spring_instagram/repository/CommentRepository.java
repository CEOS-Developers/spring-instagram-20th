package com.ceos20.spring_instagram.repository;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 포스트에 달린 댓글 조회
    List<Comment> findByPost(Post post);

    // 부모 댓글이 없는(즉, 대댓글이 아닌) 댓글 조회
    List<Comment> findByPostAndParentCommentIsNull(Post post);

    // 특정 댓글에 달린 대댓글 조회
    List<Comment> findByParentComment(Comment parentComment);
}
