package com.ceos20.instagram.domain.postLike.service;

import com.ceos20.instagram.domain.postLike.repository.PostLikeRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.domain.post.domain.Post;
import com.ceos20.instagram.domain.post.repository.PostRepository;
import com.ceos20.instagram.domain.postLike.domain.PostLike;
import com.ceos20.instagram.domain.postLike.dto.GetUserLikeResponse;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void handlePostLike(Long postId, Long memberId) {

        Optional<PostLike> postLike = postLikeRepository.findByMemberIdAndPostId(memberId, postId);
        if (postLike.isPresent()) {
            deletePostLike(postLike.get());
        } else {
            createPostLike(postId, memberId);
        }
    }

    public void deletePostLike(PostLike postLike) {
        postLikeRepository.delete(postLike);
    }

    public void createPostLike(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_MEMBER));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_POST));

        PostLike postLike = PostLike.toEntity(member, post);
        postLikeRepository.save(postLike);
    }

    public List<GetUserLikeResponse> getUsersWhoLikePost(Long postId) {

        List<Member> members = postLikeRepository.findMemberWhoLikePostByPostId(postId);

        return members.stream()
                .map(user -> GetUserLikeResponse.fromEntity(user))
                .collect(Collectors.toList());
    }
}
