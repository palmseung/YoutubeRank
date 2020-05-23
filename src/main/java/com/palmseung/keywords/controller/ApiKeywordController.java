package com.palmseung.keywords.controller;

import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.keywords.service.KeywordService;
import com.palmseung.members.domain.Member;
import com.palmseung.members.support.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import static com.palmseung.keywords.KeywordConstant.BASE_URI_KEYWORD_API;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_URI_KEYWORD_API)
public class ApiKeywordController {
    private final KeywordService keywordService;

    @PostMapping
    public ResponseEntity addKeyword(@LoginUser Member loginUser,
                                     @RequestBody MyKeywordRequestView requestView) throws UnsupportedEncodingException {
        MyKeyword myKeyword = keywordService.addMyKeyword(loginUser, requestView.getKeyword());

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/api/youtube?keyword=" + URLEncoder.encode(myKeyword.getStringKeyword(), "utf-8"))
                .build();
    }

    @GetMapping("/{keyword}")
    public ResponseEntity findMyKeyword(@LoginUser Member loginUser, @PathVariable String keyword) {
        MyKeyword myKeyword = keywordService.findMyKeyword(loginUser, keyword);

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.of(myKeyword));
    }

    @GetMapping
    public ResponseEntity findAllMyKeywords(@LoginUser Member loginUser) {
        List<MyKeyword> allMyKeyword = keywordService.findAllMyKeyword(loginUser);

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.listOf(allMyKeyword));
    }

    @DeleteMapping("/{keyword}")
    public ResponseEntity removeMyKeyword(@LoginUser Member loginUser, @PathVariable String keyword) {
        keywordService.deleteMyKeyword(loginUser, keyword);

        return ResponseEntity
                .ok()
                .build();
    }
}