package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.PostImage;
import com.ceos20.spring_boot.post.dto.PostImageRequestDto;
import com.ceos20.spring_boot.post.dto.PostImageResponseDto;
import com.ceos20.spring_boot.post.repository.PostImageRepository;
import com.ceos20.spring_boot.post.repository.PostRepository;
import com.ceos20.spring_boot.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostImageService(PostImageRepository postImageRepository, PostRepository postRepository) {
        this.postImageRepository = postImageRepository;
        this.postRepository = postRepository;
    }

    // PostImage 추가
    @Transactional
    public PostImageResponseDto addPostImage(PostImageRequestDto postImageRequestDto) {
        Post post = postRepository.findById(postImageRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postImageRequestDto.getPostId()));

        PostImage postImage = PostImageRequestDto.toEntity(postImageRequestDto, post);
        PostImage savedPostImage = postImageRepository.save(postImage);
        return PostImageResponseDto.fromEntity(savedPostImage);
    }

    // 특정 게시물에 속한 모든 이미지 조회
    @Transactional(readOnly = true)
    public List<PostImageResponseDto> getPostImagesByPostId(Long postId) {
        return postImageRepository.findByPostId(postId).stream()
                .map(PostImageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // PostImage 삭제
    @Transactional
    public void deletePostImage(Long postImageId) {
        postImageRepository.deleteById(postImageId);
    }
}
