package com.ceos20.instagram.postLike.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.postLike.domain.PostLike;
import com.ceos20.instagram.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByMemberIdAndPostId(Long memberId, Long postId);

    List<PostLike> findByPost(Post post);

    @Query("SELECT pl.member FROM PostLike pl WHERE pl.post.id = :postId")
    List<Member> findMemberWhoLikePostByPostId(@Param("postId") Long postId);

    void deleteByPostId(Long postId);
}
