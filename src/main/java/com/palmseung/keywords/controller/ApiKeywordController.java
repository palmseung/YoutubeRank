package com.palmseung.keywords.controller;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.palmseung.keywords.KeywordConstant.BASE_URI_KEYWORD_API;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_URI_KEYWORD_API)
public class ApiKeywordController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity addKeyword(@RequestBody MyKeywordRequestView requestView) {
        MyKeyword myKeyword
                = memberService.addKeyword(getLoginUser(), Keyword.builder().keyword(requestView.getKeyword()).build());

        MyKeywordResponseView responseView = MyKeywordResponseView.of(myKeyword);

        return ResponseEntity
                .ok()
                .body(responseView);
    }

    @GetMapping("/{id}")
    public ResponseEntity findMyKeyword(@PathVariable Long id) {
        MyKeyword myKeywordByMyKeywordId = memberService.findMyKeywordByMyKeywordId(getLoginUser(), id);

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.of(myKeywordByMyKeywordId));
    }

    @GetMapping
    public ResponseEntity findAllMyKeywords() {
        List<MyKeyword> allKeywords = memberService.findAllKeywords(getLoginUser());

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.listOf(allKeywords));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeMyKeyword(@PathVariable Long id) {
        if (memberService.findMyKeywordById(id).isPresent()) {
            memberService.deleteMyKeywordById(getLoginUser(), id);

            return ResponseEntity
                    .ok()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }

    private Member getLoginUser() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}