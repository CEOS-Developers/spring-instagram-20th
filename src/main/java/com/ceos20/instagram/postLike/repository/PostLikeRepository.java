package com.ceos20.instagram.postLike.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.postLike.domain.PostLike;
import com.ceos20.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);

    List<PostLike> findByPost(Post post);

    @Query("SELECT pl.user FROM PostLike pl WHERE pl.post.id = :postId")
    List<User> findUserWhoLikePostByPostId(@Param("postId") Long postId);
}
