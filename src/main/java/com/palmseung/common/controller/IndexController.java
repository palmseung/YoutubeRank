package com.palmseung.common.controller;

import com.palmseung.keywords.dto.KeywordResponseView;
import com.palmseung.keywords.service.KeywordService;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.AdminMemberResponseView;
import com.palmseung.members.jwt.LoginUser;
import com.palmseung.members.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private KeywordService keywordService;
    private MemberService memberService;

    public IndexController(KeywordService keywordService, MemberService memberService) {
        this.keywordService = keywordService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String indexPage(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        if (loginUser != null) {
            List<KeywordResponseView> keywords = keywordService.getKeywords(loginUser);
            model.addAttribute("keywords", keywords);
        }

        return "index";
    }

    @GetMapping("/admin/member")
    public String adminMemberPage(@LoginUser Member loginUser, Model model){
        model.addAttribute("loginUser", loginUser);
        List<AdminMemberResponseView> allMembers = memberService.getAllMembers(loginUser);
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("memberCount", allMembers.size());

        return "admin/admin-member";
    }

    @GetMapping("/admin/keyword")
    public String adminKeywordPage(@LoginUser Member loginUser, Model model){
        model.addAttribute("loginUser", loginUser);
        List<KeywordResponseView> allKeywords = keywordService.getAllKeywords(loginUser);
        model.addAttribute("allKeywords", allKeywords);
        model.addAttribute("keywordCount", allKeywords.size());

        return "admin/admin-keyword";
    }

}