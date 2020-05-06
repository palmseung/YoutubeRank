package com.palmseung.members.controller;

import com.palmseung.members.MemberConstant;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.*;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MemberConstant.BASE_URI_MEMBER_API)
public class ApiMemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ResponseEntity create(@RequestBody CreateMemberRequestView request) {
        Member createdMember = memberService.create(request);
        CreateMemberResponseView response = CreateMemberResponseView.of(createdMember);

        return ResponseEntity
                .created(URI.create(MemberConstant.BASE_URI_MEMBER_API + "/" + createdMember.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberResponseView> deleteById(@PathVariable Long id) {
        memberService.delete(memberService.findById(id));

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseView> login(@RequestBody LoginRequestView request) {
        Member member = memberService.login(request.getEmail(), request.getPassword());
        String token = jwtTokenProvider.createToken(member.getEmail());

        return ResponseEntity
                .created(URI.create("/oauth/token"))
                .body(LoginResponseView.of(token));
    }

    @GetMapping("/my-info/{id}")
    public ResponseEntity<MyInfoResponseView> retrieveMyInfo(@PathVariable Long id) {
        Member member = memberService.findById(id);

        return ResponseEntity
                .ok()
                .body(MyInfoResponseView.of(member));
    }

    @PutMapping("/my-info/{id}")
    public ResponseEntity<UpdateMemberResponseView> updateMyInfo(@PathVariable Long id,
                                                                 @RequestBody UpdateMemberRequestView requestView) {
        Member oldMember = memberService.findById(id);
        Member updatedMember = memberService.updateInfo(getLoginUser(), oldMember, Member.of(requestView));

        return ResponseEntity
                .ok()
                .body(UpdateMemberResponseView.of(updatedMember));
    }

    private Member getLoginUser() {
        return (Member) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}