package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
    List<PostImage> findPostImageByPost(Long postId);
    void deletePostImageByPost(Long postId);
}
