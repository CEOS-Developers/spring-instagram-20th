package com.ceos20.instagram.post.service;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.post.dto.PostImageDto;
import com.ceos20.instagram.post.repository.PostImageRepository;
import com.ceos20.instagram.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostImageService {
    private PostRepository postRepository;
    private PostImageRepository postImageRepository;

    @Transactional
    public void create(PostImageDto postImageDto) {
        PostImage postImage = postImageDto.toEntity(postImageDto.getPost());
        postImageRepository.save(postImage);
    }

    public List<PostImage> getImages(Long postId) {
        List<PostImage> images = postImageRepository.findPostImageByPost(postId);
        if (images.isEmpty()) {
            throw new IllegalStateException("No Image");

        }
        return images;

    }
    public void delete(Long postId) {
        postImageRepository.deletePostImageByPost(postId);
    }
}
