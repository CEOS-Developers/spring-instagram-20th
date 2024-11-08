package com.ceos20.instagram.post.controller;

import com.ceos20.instagram.post.dto.PostRequestDto;
import com.ceos20.instagram.post.dto.PostResponseDto;
import com.ceos20.instagram.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody @Valid PostRequestDto postRequestDto, @RequestParam("nickname") String nickname) {
        postService.create(postRequestDto, nickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        final PostResponseDto response = postService.getPost(postId);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
