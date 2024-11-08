package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByWriter_Id(Long userId);
}
