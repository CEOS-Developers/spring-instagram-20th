package com.ceos20.instagram.comment.repository;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    // 댓글 저장
    public void save(Comment comment) {
        em.persist(comment);
    }

    // 특정 게시글의 댓글 조회
    public List<Comment> findByPost(Post post) {
        return em.createQuery("select c from Comment c where c.post = :post", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }

    // 특정 댓글의 자식 댓글 조회
    public List<Comment> findChildByComment(Comment comment) {
        return em.createQuery("select c from Comment c where c.parentComment = :comment", Comment.class)
                .setParameter("comment", comment)
                .getResultList();
    }
}
