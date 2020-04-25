package com.palmseung.members.controller;

import com.palmseung.members.MemberConstant;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.MemberResponseView;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.dto.CreateMemberRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MemberConstant.BASE_URI_USER_API)
public class ApiMemberController {
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity create(@RequestBody CreateMemberRequestView request) {
        Member createdMember = memberService.create(request);
        MemberResponseView response = MemberResponseView.of(createdMember);

        return ResponseEntity
                .created(URI.create(MemberConstant.BASE_URI_USER_API + "/" + createdMember.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
