package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
}
