package com.ceos20.instagram.domain.member.controller;

import com.ceos20.instagram.global.common.annotation.Login;
import com.ceos20.instagram.global.common.response.ApiResponse;
import com.ceos20.instagram.global.common.response.ResponseBuilder;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.dto.GetMemberInfoResponse;
import com.ceos20.instagram.domain.member.dto.UpdateMemberInfoRequest;
import com.ceos20.instagram.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateMemberInfo(
            @RequestBody UpdateMemberInfoRequest updateMemberInfoRequest,
            @Login Member member) {

        memberService.updateMemberInfo(updateMemberInfoRequest, member);
        return ResponseBuilder.createApiResponse("멤버 정보 수정 완료", null);
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<GetMemberInfoResponse>> getMemberInfoById(@Login Member member) {

        GetMemberInfoResponse getMemberInfoResponse = memberService.getMemberInfoById(member.getId());
        return ResponseBuilder.createApiResponse("멤버 정보 조회 완료", getMemberInfoResponse);
    }
}
