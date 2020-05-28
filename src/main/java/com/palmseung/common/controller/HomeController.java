package com.palmseung.common.controller;

import com.palmseung.keywords.dto.KeywordResponseView;
import com.palmseung.keywords.service.KeywordService;
import com.palmseung.members.domain.Member;
import com.palmseung.members.jwt.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private KeywordService keywordService;

    public HomeController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/")
    public String indexPage(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);

        if (loginUser != null) {
            List<KeywordResponseView> keywords = keywordService.getKeywords(loginUser);
            model.addAttribute("keywords", keywords);

            if (loginUser.isAdmin()) {
                model.addAttribute("admin", loginUser);
            }
        }

        return "index";
    }
}