package com.palmseung.keyword.controller;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.dto.KeywordResponseView;
import com.palmseung.member.domain.Member;
import com.palmseung.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_URI_KEYWORD_API)
public class ApiKeywordController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity addKeyword(@RequestBody String keyword) {
        memberService.addKeyword(getLoginUser(), Keyword.builder().keyword(keyword).build());
        Member member = memberService.findByEmail(getLoginUser().getEmail());

        return ResponseEntity
                .ok()
                .body(KeywordResponseView.listOf(member.getKeywords()));
    }

    private Member getLoginUser() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
