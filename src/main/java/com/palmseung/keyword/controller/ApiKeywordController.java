package com.palmseung.keyword.controller;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.domain.MyKeyword;
import com.palmseung.keyword.dto.MyKeywordResponseView;
import com.palmseung.member.domain.Member;
import com.palmseung.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_URI_KEYWORD_API)
public class ApiKeywordController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity addKeyword(@RequestBody String keyword) {
        MyKeyword myKeyword
                = memberService.addKeyword(getLoginUser(), Keyword.builder().keyword(keyword).build());

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.builder()
                        .myKeywordId(myKeyword.getId())
                        .keyword(myKeyword.getKeyword().getKeyword()));
    }

    @GetMapping
    public ResponseEntity findAllMyKeywords() {
        List<Keyword> allKeywords = memberService.findAllKeywords(getLoginUser());

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.listOf(allKeywords));
    }

    private Member getLoginUser() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
