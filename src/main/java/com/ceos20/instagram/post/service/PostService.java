package com.ceos20.instagram.post.service;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.dto.PostCreateDto;
import com.ceos20.instagram.post.repository.PostImageRepository;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostImageRepository postImageRepository;

    @Transactional
    public void create(PostCreateDto postCreateDto) {
        Post post = postCreateDto.toEntity(postCreateDto.getContent(), postCreateDto.getWriter());
        postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findPostById(postId)
                .orElseThrow(IllegalAccessError::new);
    }

    public List<Post> getPosts(Long userId) {
        List<Post> posts = postRepository.findPostByWriter_Id(userId);
        if (posts.isEmpty()) {
            throw new IllegalStateException("No post");
        }
        return posts;
    }

    @Transactional
    public void delete(Long postId){
        postRepository.deletePostById(postId);
    }





}
