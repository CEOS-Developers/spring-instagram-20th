package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
    List<PostImage> findByPost(Post post);
    void deleteByPost(Post post);
}
