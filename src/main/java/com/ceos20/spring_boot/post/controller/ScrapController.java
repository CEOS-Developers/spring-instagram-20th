package com.ceos20.spring_boot.post.controller;

import com.ceos20.spring_boot.post.domain.Scrap;
import com.ceos20.spring_boot.post.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scraps")
public class ScrapController {

    private final ScrapService scrapService;

    @Autowired
    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    // 스크랩 추가 (POST 요청)
    @PostMapping
    public ResponseEntity<Scrap> addScrap(@RequestBody Scrap scrap) {
        Scrap createdScrap = scrapService.addScrap(scrap);
        return ResponseEntity.ok(createdScrap);
    }

    // 스크랩 삭제 (DELETE 요청)
    @DeleteMapping("/{scrapId}")
    public ResponseEntity<Void> deleteScrap(@PathVariable Long scrapId) {
        scrapService.deleteScrap(scrapId);
        return ResponseEntity.noContent().build();
    }

    // 특정 유저의 모든 스크랩 조회 (GET 요청)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Scrap>> getScrapsByUserId(@PathVariable Long userId) {
        List<Scrap> scraps = scrapService.getScrapsByUserId(userId);
        return ResponseEntity.ok(scraps);
    }
}
