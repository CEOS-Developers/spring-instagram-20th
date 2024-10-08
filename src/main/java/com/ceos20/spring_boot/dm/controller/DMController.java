package com.ceos20.spring_boot.dm.controller;

import com.ceos20.spring_boot.dm.domain.DM;
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
    public ResponseEntity<DM> sendDM(@RequestBody DM dm) {
        DM createdDM = dmService.sendDM(dm);
        return ResponseEntity.ok(createdDM);
    }

    // DM 조회 (GET 요청)
    @GetMapping("/{dmId}")
    public ResponseEntity<DM> getDMById(@PathVariable Long dmId) {
        DM dm = dmService.getDMById(dmId);
        return ResponseEntity.ok(dm);
    }

    // DM 삭제 (DELETE 요청)
    @DeleteMapping("/{dmId}")
    public ResponseEntity<Void> deleteDM(@PathVariable Long dmId) {
        dmService.deleteDM(dmId);
        return ResponseEntity.noContent().build();
    }
}
