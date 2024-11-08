package com.ceos20.spring_boot.dm.service;

import com.ceos20.spring_boot.dm.domain.DM;
import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.dm.dto.DMRequestDto;
import com.ceos20.spring_boot.dm.dto.DMResponseDto;
import com.ceos20.spring_boot.dm.repository.DMRepository;
import com.ceos20.spring_boot.dm.repository.DMRoomRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DMService {

    private final DMRepository dmRepository;
    private final UserRepository userRepository;
    private final DMRoomRepository dmRoomRepository;

    @Autowired
    public DMService(DMRepository dmRepository, UserRepository userRepository, DMRoomRepository dmRoomRepository) {
        this.dmRepository = dmRepository;
        this.userRepository = userRepository;
        this.dmRoomRepository = dmRoomRepository;
    }

    // DM 생성
    @Transactional
    public DMResponseDto sendDM(DMRequestDto dmRequestDto) {
        User sender = userRepository.findById(dmRequestDto.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dmRequestDto.getSenderId()));
        User receiver = userRepository.findById(dmRequestDto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dmRequestDto.getReceiverId()));
        DMRoom dmRoom = dmRoomRepository.findById(dmRequestDto.getDmRoomId())
                .orElseThrow(() -> new RuntimeException("DM Room not found with id: " + dmRequestDto.getDmRoomId()));

        DM refDM = dmRequestDto.getRefDMId() != null ? dmRepository.findById(dmRequestDto.getRefDMId())
                .orElse(null) : null;

        DM dm = DMRequestDto.toEntity(dmRequestDto, sender, receiver, dmRoom, refDM);
        DM savedDM = dmRepository.save(dm);
        return DMResponseDto.fromEntity(savedDM);
    }

    // 특정 DM 조회
    @Transactional(readOnly = true)
    public DMResponseDto getDMById(Long dmId) {
        DM dm = dmRepository.findById(dmId)
                .orElseThrow(() -> new RuntimeException("DM not found with id: " + dmId));
        return DMResponseDto.fromEntity(dm);
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
