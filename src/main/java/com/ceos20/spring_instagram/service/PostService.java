package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 새로운 포스트를 작성하는 메서드
    public Post createPost(Long memberId, String image, String caption) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다.");
        }

        Post post = Post.builder()
                .member(member.get())
                .image(image)
                .caption(caption)
                .build();

        return postRepository.save(post); // 포스트 저장
    }

    // 특정 포스트를 조회하는 메서드
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 모든 포스트를 조회하는 메서드
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 회원의 모든 포스트를 조회하는 메서드
    public List<Post> getPostsByMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다.");
        }

        return postRepository.findByMember(member.get());
    }

    // 포스트를 업데이트하는 메서드
    public Post updatePost(Long postId, String newCaption, String newImage) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 포스트를 찾을 수 없습니다.");
        }

        Post post = postOptional.get();
        post.setCaption(newCaption);
        post.setImage(newImage);

        return postRepository.save(post); // 업데이트된 포스트 저장
    }

    // 포스트를 삭제하는 메서드
    public void deletePost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 포스트를 찾을 수 없습니다.");
        }

        postRepository.deleteById(postId);
    }
}
