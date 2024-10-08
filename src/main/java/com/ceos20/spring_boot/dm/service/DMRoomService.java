package com.ceos20.spring_boot.dm.service;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.dm.repository.DMRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DMRoomService {

    private final DMRoomRepository dmRoomRepository;

    @Autowired
    public DMRoomService(DMRoomRepository dmRoomRepository) {
        this.dmRoomRepository = dmRoomRepository;
    }

    // DMRoom 생성
    @Transactional
    public DMRoom createDMRoom(DMRoom dmRoom) {
        // 비즈니스 로직 추가 가능
        return dmRoomRepository.save(dmRoom);
    }

    // 특정 DMRoom 조회
    @Transactional(readOnly = true)
    public DMRoom getDMRoomById(Long dmRoomId) {
        return dmRoomRepository.findById(dmRoomId)
                .orElseThrow(() -> new RuntimeException("DM Room not found with id: " + dmRoomId));
    }

    // DMRoom 삭제
    @Transactional
    public void deleteDMRoom(Long dmRoomId) {
        if (!dmRoomRepository.existsById(dmRoomId)) {
            throw new RuntimeException("DM Room not found with id: " + dmRoomId);
        }
        dmRoomRepository.deleteById(dmRoomId);
    }
}
