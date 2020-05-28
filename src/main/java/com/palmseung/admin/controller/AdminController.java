package com.palmseung.admin.controller;

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
public class AdminController {
    private MemberService memberService;
    private KeywordService keywordService;

    public AdminController(MemberService memberService, KeywordService keywordService) {
        this.memberService = memberService;
        this.keywordService = keywordService;
    }

    @GetMapping("/admin/members")
    public String adminMemberPage(@LoginUser Member loginUser, Model model) {
        List<AdminMemberResponseView> allMembers = memberService.getAllMembers();
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("memberCount", allMembers.size());

        return "admin/layout/admin-member-list";
    }

    @GetMapping("/admin/keywords")
    public String adminKeywordPage(@LoginUser Member loginUser, Model model) {
        List<KeywordResponseView> allKeywords = keywordService.getAllKeywords(loginUser);
        model.addAttribute("allKeywords", allKeywords);
        model.addAttribute("keywordCount", allKeywords.size());

        return "admin/layout/admin-keyword-list";
    }
}
