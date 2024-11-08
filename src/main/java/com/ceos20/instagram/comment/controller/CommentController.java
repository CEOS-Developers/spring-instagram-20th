package com.ceos20.instagram.comment.controller;

import com.ceos20.instagram.comment.Service.CommentService;
import com.ceos20.instagram.comment.dto.CommentCreateDto;
import com.ceos20.instagram.comment.dto.CommentsResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.ResponseSupportConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity createPost(@PathVariable Long postId, @RequestBody @Valid CommentCreateDto commentCreateDto, @RequestParam("nickname") String nickname) {
        commentService.create(postId, commentCreateDto, nickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentsResponseDto>> getComments(@PathVariable Long postId) {
        final List<CommentsResponseDto> responses = commentService.getComments(postId);
        return ResponseEntity.ok().body(responses);
    }
}
