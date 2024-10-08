package com.ceos20.instagram.image.repository;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPost(Post post);
}
