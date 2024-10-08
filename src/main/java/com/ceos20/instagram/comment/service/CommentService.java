package com.ceos20.instagram.comment.service;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.comment.dto.CreateChildCommentRequest;
import com.ceos20.instagram.comment.dto.CreateCommentRequest;
import com.ceos20.instagram.comment.dto.GetCommentResponse;
import com.ceos20.instagram.comment.dto.UpdateCommentRequest;
import com.ceos20.instagram.comment.repository.CommentRepository;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public void createComment(CreateCommentRequest createCommentRequest, User user) {

        Post post = postRepository.findById(createCommentRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));

        Comment comment = createCommentRequest.toEntity(post, user);
        commentRepository.save(comment);
    }

    // 대댓글 작성
    @Transactional
    public void createChildComment(CreateChildCommentRequest createChildCommentRequest, User user) {

        Post post = postRepository.findById(createChildCommentRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));

        Comment parentComment = commentRepository.findById(createChildCommentRequest.getParentCommentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));

        Comment comment = createChildCommentRequest.toEntity(parentComment, post, user);
        commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public void updateContent(UpdateCommentRequest updateCommentRequest) {

        Comment comment = commentRepository.findById(updateCommentRequest.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
        comment.updateContent(updateCommentRequest.getContent());
        commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
        commentRepository.delete(comment);
    }

    // 특정 post의 댓글 리스트 반환 -> 근데 자식 댓글은 잘 모르겠음..
    public List<GetCommentResponse> getCommentsByPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 post 입니다."));

        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(comment -> GetCommentResponse.fromEntity(comment))
                .collect(Collectors.toList());
    }
}
