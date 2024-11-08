package com.ceos20.spring_boot.dm.controller;

import com.ceos20.spring_boot.dm.dto.DMRequestDto;
import com.ceos20.spring_boot.dm.dto.DMResponseDto;
import com.ceos20.spring_boot.dm.service.DMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dms")
public class DMController {

    private final DMService dmService;

    @Autowired
    public DMController(DMService dmService) {
        this.dmService = dmService;
    }

    // DM 생성 (POST 요청)
    @PostMapping
    public ResponseEntity<DMResponseDto> sendDM(@RequestBody DMRequestDto dmRequestDto) {
        DMResponseDto createdDM = dmService.sendDM(dmRequestDto);
        return ResponseEntity.ok(createdDM);
    }

    // DM 조회 (GET 요청)
    @GetMapping("/{dmId}")
    public ResponseEntity<DMResponseDto> getDMById(@PathVariable Long dmId) {
        DMResponseDto dm = dmService.getDMById(dmId);
        return ResponseEntity.ok(dm);
    }

    // DM 삭제 (DELETE 요청)
    @DeleteMapping("/{dmId}")
    public ResponseEntity<Void> deleteDM(@PathVariable Long dmId) {
        dmService.deleteDM(dmId);
        return ResponseEntity.noContent().build();
    }
}
