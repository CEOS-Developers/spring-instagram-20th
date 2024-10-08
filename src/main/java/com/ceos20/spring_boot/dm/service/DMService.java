package com.ceos20.spring_boot.dm.service;

import com.ceos20.spring_boot.dm.domain.DM;
import com.ceos20.spring_boot.dm.repository.DMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DMService {

    private final DMRepository dmRepository;

    @Autowired
    public DMService(DMRepository dmRepository) {
        this.dmRepository = dmRepository;
    }

    // DM 생성
    @Transactional
    public DM sendDM(DM dm) {
        // 비즈니스 로직 추가 가능 (예: 메시지 길이 제한 등)
        return dmRepository.save(dm);
    }

    // 특정 DM 조회
    @Transactional(readOnly = true)
    public DM getDMById(Long dmId) {
        return dmRepository.findById(dmId)
                .orElseThrow(() -> new RuntimeException("DM not found with id: " + dmId));
    }

    // DM 삭제
    @Transactional
    public void deleteDM(Long dmId) {
        if (!dmRepository.existsById(dmId)) {
            throw new RuntimeException("DM not found with id: " + dmId);
        }
        dmRepository.deleteById(dmId);
    }
}
