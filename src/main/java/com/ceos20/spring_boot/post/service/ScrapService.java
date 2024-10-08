package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.Scrap;
import com.ceos20.spring_boot.post.repository.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    @Autowired
    public ScrapService(ScrapRepository scrapRepository) {
        this.scrapRepository = scrapRepository;
    }

    // 스크랩 추가
    @Transactional
    public Scrap addScrap(Scrap scrap) {
        return scrapRepository.save(scrap);
    }

    // 스크랩 삭제
    @Transactional
    public void deleteScrap(Long scrapId) {
        scrapRepository.deleteById(scrapId);
    }

    // 특정 유저의 모든 스크랩 조회
    @Transactional(readOnly = true)
    public List<Scrap> getScrapsByUserId(Long userId) {
        return scrapRepository.findByUserId(userId);
    }
}
