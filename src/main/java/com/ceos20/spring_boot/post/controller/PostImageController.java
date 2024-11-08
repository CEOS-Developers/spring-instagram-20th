package com.ceos20.spring_boot.post.controller;

import com.ceos20.spring_boot.post.dto.PostImageRequestDto;
import com.ceos20.spring_boot.post.dto.PostImageResponseDto;
import com.ceos20.spring_boot.post.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postimages")
public class PostImageController {

    private final PostImageService postImageService;

    @Autowired
    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    // PostImage 추가 (POST 요청)
    @PostMapping
    public ResponseEntity<PostImageResponseDto> addPostImage(@RequestBody PostImageRequestDto postImageRequestDto) {
        PostImageResponseDto createdPostImage = postImageService.addPostImage(postImageRequestDto);
        return ResponseEntity.ok(createdPostImage);
    }

    // 특정 게시물의 모든 이미지 조회 (GET 요청)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostImageResponseDto>> getPostImagesByPostId(@PathVariable Long postId) {
        List<PostImageResponseDto> postImages = postImageService.getPostImagesByPostId(postId);
        return ResponseEntity.ok(postImages);
    }

    // PostImage 삭제 (DELETE 요청)
    @DeleteMapping("/{postImageId}")
    public ResponseEntity<Void> deletePostImage(@PathVariable Long postImageId) {
        postImageService.deletePostImage(postImageId);
        return ResponseEntity.noContent().build();
    }
}
