package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.PostImage;
import com.ceos20.spring_boot.post.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;

    @Autowired
    public PostImageService(PostImageRepository postImageRepository) {
        this.postImageRepository = postImageRepository;
    }

    // PostImage 추가
    @Transactional
    public PostImage addPostImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

    // 특정 게시물에 속한 모든 이미지 조회
    @Transactional(readOnly = true)
    public List<PostImage> getPostImagesByPostId(Long postId) {
        return postImageRepository.findByPostId(postId);
    }

    // PostImage 삭제
    @Transactional
    public void deletePostImage(Long postImageId) {
        postImageRepository.deleteById(postImageId);
    }
}
