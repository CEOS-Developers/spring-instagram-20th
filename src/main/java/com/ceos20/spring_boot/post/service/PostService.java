package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.Post;
import com.ceos20.spring_boot.post.dto.PostRequestDto;
import com.ceos20.spring_boot.post.dto.PostResponseDto;
import com.ceos20.spring_boot.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시물 생성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = PostRequestDto.toEntity(postRequestDto);
        Post savedPost = postRepository.save(post);
        return PostResponseDto.fromEntity(savedPost);
    }

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 게시물 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return PostResponseDto.fromEntity(post);
    }

    // 게시물 삭제
    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }
}
