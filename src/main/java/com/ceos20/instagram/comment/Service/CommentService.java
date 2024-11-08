package com.ceos20.instagram.comment.Service;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.comment.dto.CommentCreateDto;
import com.ceos20.instagram.comment.dto.CommentsResponseDto;
import com.ceos20.instagram.comment.repository.CommentRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(Long postId, CommentCreateDto commentCreateDto, final String nickname) {
        final User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_COMMENT));

        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_POST));
        post.increaseCommentCount();

        final Comment comment = commentCreateDto.toEntity(user, post);

        commentRepository.save(comment);
    }

    public List<CommentsResponseDto> getComments(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_POST));

        final List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(CommentsResponseDto::from)
                .toList();
    }





}
