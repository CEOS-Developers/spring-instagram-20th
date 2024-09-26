package com.ceos20.instagram.comment.repository;

import com.ceos20.instagram.comment.domain.CommentUserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentUserTagRepository extends JpaRepository<CommentUserTag, Long> {
}
