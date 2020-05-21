package com.palmseung.keywords.controller;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.LoginUser;
import com.palmseung.members.support.UserMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity addKeyword(@LoginUser Member loginUser,
                                     @RequestBody MyKeywordRequestView requestView) {
        MyKeyword myKeyword
                = memberService.addKeyword(loginUser, Keyword.builder().keyword(requestView.getKeyword()).build());

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/api/youtube?keyword="+myKeyword.getStringKeyword())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findMyKeyword(@LoginUser Member loginUser, @PathVariable Long id) {
        MyKeyword myKeywordByMyKeywordId = memberService.findMyKeywordByMyKeywordId(loginUser, id);

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.of(myKeywordByMyKeywordId));
    }

    @GetMapping
    public ResponseEntity findAllMyKeywords(@LoginUser Member loginUser) {
        List<MyKeyword> allKeywords = memberService.findAllKeywords(loginUser);

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.listOf(allKeywords));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeMyKeyword(@LoginUser Member loginUser, @PathVariable Long id) {
        if (memberService.findMyKeywordById(id).isPresent()) {
            memberService.deleteMyKeywordById(loginUser, id);

            return ResponseEntity
                    .ok()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}