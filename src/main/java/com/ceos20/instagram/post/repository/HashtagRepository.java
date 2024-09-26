package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
