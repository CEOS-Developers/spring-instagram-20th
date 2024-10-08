package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Likes;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.LikesRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 좋아요 추가
    public Likes addLike(Long postId, Long memberId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Member> member = memberRepository.findById(memberId);

        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 포스트를 찾을 수 없습니다.");
        }

        if (member.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다.");
        }

        Likes like = Likes.builder()
                .post(post.get())
                .member(member.get())
                .build();

        return likesRepository.save(like); // 좋아요 저장
    }

    // 특정 포스트의 모든 좋아요 조회
    public List<Likes> getLikesByPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 포스트를 찾을 수 없습니다.");
        }

        return likesRepository.findByPost(post.get());
    }

    // 특정 회원이 누른 모든 좋아요 조회
    public List<Likes> getLikesByMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 회원을 찾을 수 없습니다.");
        }

        return likesRepository.findByMember(member.get());
    }

    // 좋아요 삭제
    public void deleteLike(Long likeId) {
        Optional<Likes> like = likesRepository.findById(likeId);

        if (like.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 좋아요를 찾을 수 없습니다.");
        }

        likesRepository.deleteById(likeId);
    }
}
