package com.ceos20.instagram.domain.comment.repository;

import com.ceos20.instagram.domain.comment.domain.Comment;
import com.ceos20.instagram.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
