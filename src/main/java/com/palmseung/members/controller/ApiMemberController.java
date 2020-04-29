package com.palmseung.members.controller;

import com.palmseung.members.MemberConstant;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.dto.LoginRequestView;
import com.palmseung.members.dto.LoginResponseView;
import com.palmseung.members.dto.MemberResponseView;
import com.palmseung.members.service.MemberService;
import com.palmseung.support.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MemberConstant.BASE_URI_MEMBER_API)
public class ApiMemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ResponseEntity<MemberResponseView> create(@RequestBody CreateMemberRequestView request) {
        Member createdMember = memberService.create(request);
        MemberResponseView response = MemberResponseView.of(createdMember);

        return ResponseEntity
                .created(URI.create(MemberConstant.BASE_URI_MEMBER_API + "/" + createdMember.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberResponseView> deleteById(@PathVariable Long id) {
        memberService.delete(memberService.findById(id));

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseView> login(@RequestBody LoginRequestView request) {
        Member member = memberService.findByEmail(request.getEmail());
        boolean isMatch = passwordEncoder.matches(request.getPassword(), member.getPassword());

        if (!isMatch) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        String token = jwtTokenProvider.createToken(member.getEmail());
        LoginResponseView response = new LoginResponseView(token, "Bearer ");
        return ResponseEntity
                .created(URI.create("/oauth/token"))
                .body(response);
    }
}