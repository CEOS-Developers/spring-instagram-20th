package com.ceos20.instagram.post.service;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.dto.PostLikeDto;
import com.ceos20.instagram.post.repository.PostLikeRepository;
import com.ceos20.instagram.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostLikeService {
    private PostLikeRepository postLikeRepository;
    private PostService postService;

    public void createLike(PostLikeDto postLikeDto) {
        User user = postLikeDto.getUser();
        Post post = postLikeDto.getPost();

        post.increaseLikeCount();
        postLikeRepository.save(postLikeDto.toEntity(user, post));
    }

    public  void deleteLike(PostLikeDto postLikeDto) {

    }


}
