package com.ceos20.instagram.post.service;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.comment.repository.CommentRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.post.dto.PostRequestDto;
import com.ceos20.instagram.post.dto.PostResponseDto;
import com.ceos20.instagram.post.repository.PostImageRepository;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final CommentRepository commentRepository;
    private final PostImageService postImageService;

    @Transactional
    public void create(PostRequestDto postRequestDto, final String nickname) {
        final User writer = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new NotFoundException(ExceptionCode.NOT_FOUND_USER));
        writer.increasePostCount();

        final Post post = postRequestDto.toEntity(writer);

        postImageService.uploadImage(post, postRequestDto.getImages());

        postRepository.save(post);
    }

    public PostResponseDto getPost(Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException(ExceptionCode.NOT_FOUND_POST));

        final List<PostImage> images = postImageRepository.findByPost(post);

        final List<String> imageUrls = images.stream().map(PostImage::getPath)
                .collect(Collectors.toList());

        return PostResponseDto.of(post, imageUrls);
    }

    @Transactional
    public void delete(Long postId, String nickname){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_POST));

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new NotFoundException(ExceptionCode.NOT_FOUND_USER));

        if(post.getWriter() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        postRepository.deleteById(postId);
    }





}
