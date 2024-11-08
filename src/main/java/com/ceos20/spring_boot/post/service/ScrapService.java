package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.Post;
import com.ceos20.spring_boot.post.domain.Scrap;
import com.ceos20.spring_boot.post.dto.ScrapRequestDto;
import com.ceos20.spring_boot.post.dto.ScrapResponseDto;
import com.ceos20.spring_boot.post.repository.PostRepository;
import com.ceos20.spring_boot.post.repository.ScrapRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScrapService(ScrapRepository scrapRepository, PostRepository postRepository, UserRepository userRepository) {
        this.scrapRepository = scrapRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 스크랩 추가
    @Transactional
    public ScrapResponseDto addScrap(ScrapRequestDto scrapRequestDto) {
        Post post = postRepository.findById(scrapRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + scrapRequestDto.getPostId()));

        User user = userRepository.findById(scrapRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + scrapRequestDto.getUserId()));

        Scrap scrap = ScrapRequestDto.toEntity(scrapRequestDto, post, user);
        Scrap savedScrap = scrapRepository.save(scrap);
        return ScrapResponseDto.fromEntity(savedScrap);
    }

    // 스크랩 삭제
    @Transactional
    public void deleteScrap(Long scrapId) {
        scrapRepository.deleteById(scrapId);
    }

    // 특정 유저의 모든 스크랩 조회
    @Transactional(readOnly = true)
    public List<ScrapResponseDto> getScrapsByUserId(Long userId) {
        return scrapRepository.findByUserId(userId).stream()
                .map(ScrapResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
