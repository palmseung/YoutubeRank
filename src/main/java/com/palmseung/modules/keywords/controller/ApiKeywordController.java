package com.palmseung.modules.keywords.controller;

import com.palmseung.modules.keywords.domain.MyKeyword;
import com.palmseung.modules.keywords.dto.*;
import com.palmseung.modules.keywords.service.KeywordService;
import com.palmseung.modules.members.LoginUser;
import com.palmseung.modules.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.palmseung.modules.keywords.KeywordConstant.BASE_URI_KEYWORD_API;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_URI_KEYWORD_API)
public class ApiKeywordController {
    private final KeywordService keywordService;

    @PostMapping
    public ResponseEntity addKeyword(@LoginUser Member loginUser,
                                     @RequestBody MyKeywordRequestView requestView) throws UnsupportedEncodingException {
        MyKeyword myKeyword = keywordService.addMyKeyword(loginUser, requestView.getKeyword());

        MyKeywordResource resource = new MyKeywordResource(MyKeywordResponseView.of(myKeyword));
        resource.add(linkTo(ApiKeywordController.class).
                withSelfRel());
        resource.add(new Link("/docs/api-guide.html#resources-keywords-add-my-keyword")
                .withRel("profile"));
        resource.add(linkTo(ApiKeywordController.class)
                .slash(myKeyword.getStringKeyword())
                .withRel("delete-my-keyword"));
        resource.add(linkTo(ApiKeywordController.class)
                .slash(myKeyword.getStringKeyword())
                .withRel("retrieve-my-keyword"));

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/api/youtube?keyword=" + URLEncoder.encode(myKeyword.getStringKeyword(), "utf-8"))
                .body(resource);
    }

    @GetMapping("/{keyword}")
    public ResponseEntity findMyKeyword(@LoginUser Member loginUser, @PathVariable String keyword) {
        MyKeyword myKeyword = keywordService.findMyKeyword(loginUser, keyword);

        MyKeywordResponseResource resource = new MyKeywordResponseResource(MyKeywordResponseView.of(myKeyword));
        resource.add(linkTo(ApiKeywordController.class).slash(keyword)
                .withSelfRel());
        resource.add(new Link("/docs/api-guide.html#resources-keywords-retrieve-my-keyword")
                .withRel("profile"));
        resource.add(linkTo(ApiKeywordController.class)
                .slash(myKeyword.getStringKeyword())
                .withRel("delete-my-keyword"));

        return ResponseEntity
                .ok()
                .body(resource);
    }

    @GetMapping
    public ResponseEntity findAllMyKeywords(@LoginUser Member loginUser) {
        List<MyKeyword> allMyKeyword = keywordService.findAllMyKeyword(loginUser);

//        MyKeywordListResponseResource resource
//                = new MyKeywordResponseResource(MyKeywordResponseView.listOf(allMyKeyword));
//        resource.add(linkTo(ApiKeywordController.class).withSelfRel());

        return ResponseEntity
                .ok()
                .body(MyKeywordResponseView.listOf(allMyKeyword));
    }

    @DeleteMapping("/{keyword}")
    public ResponseEntity removeMyKeyword(@LoginUser Member loginUser, @PathVariable String keyword) {
        keywordService.deleteMyKeyword(loginUser, keyword);

        MyKeywordResponseResource resource = new MyKeywordResponseResource(new MyKeywordResponseView());
        resource.add(linkTo(ApiKeywordController.class).slash(keyword)
                .withSelfRel());
        resource.add(new Link("/docs/api-guide.html#resources-keywords-delete-my-keyword")
                .withRel("profile"));

        return ResponseEntity
                .ok()
                .body(resource);
    }
}