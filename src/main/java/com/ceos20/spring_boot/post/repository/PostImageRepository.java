package com.ceos20.spring_boot.post.repository;

import com.ceos20.spring_boot.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    // 특정 게시물에 속한 이미지들 조회
    List<PostImage> findByPostId(Long postId);
}
