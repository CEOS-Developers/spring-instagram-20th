package com.ceos20.instagram.post.controller;

import com.ceos20.instagram.common.annotation.Login;
import com.ceos20.instagram.common.response.ApiResponse;
import com.ceos20.instagram.common.response.ResponseBuilder;
import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.post.dto.CreatePostRequest;
import com.ceos20.instagram.post.dto.GetPostResponse;
import com.ceos20.instagram.post.dto.UpdatePostContentRequest;
import com.ceos20.instagram.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody CreatePostRequest createPostRequest,
            @Login Member member) {

        postService.createPost(createPostRequest, member);
        return ResponseBuilder.createApiResponse("게시글 작성 완료", null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetPostResponse>>> getAllPosts() {

        List<GetPostResponse> getPostResponses = postService.getAllPosts();
        return ResponseBuilder.createApiResponse("모든 게시글 조회 완료", getPostResponses);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePostContentById(
            @PathVariable Long postId,
            @RequestBody UpdatePostContentRequest updatePostContentRequest
            ) {
        postService.updatePostContent(updatePostContentRequest, postId);
        return ResponseBuilder.createApiResponse("게시글 수정 완료", null);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable Long postId) {

        postService.deletePost(postId);
        return ResponseBuilder.createApiResponse("게시글 삭제 완료", null);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<GetPostResponse>> getPostById(@PathVariable Long postId) {

        GetPostResponse getPostResponse = postService.getPostById(postId);
        return ResponseBuilder.createApiResponse("게시글 단건 조회 완료", getPostResponse);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<List<GetPostResponse>>> getAllPostsByMemberId(@PathVariable Long memberId) {

        List<GetPostResponse> getPostResponses = postService.getAllPostsByMemberId(memberId);
        return ResponseBuilder.createApiResponse("특정 멤버의 모든 게시글 조회 완료", getPostResponses);
    }
}
