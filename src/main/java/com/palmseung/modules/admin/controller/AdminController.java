package com.palmseung.modules.admin.controller;

import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.admin.service.AdminService;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/members")
    public String adminMemberPage(Model model) {
        List<AdminMemberResponseView> allMembers = adminService.getAllMembers();
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("memberCount", allMembers.size());

        return "admin/layout/admin-member-list";
    }

    @GetMapping("/admin/keywords")
    public String adminKeywordPage(Model model) {
        List<KeywordResponseView> allKeywords = adminService.getAllKeywords();
        model.addAttribute("allKeywords", allKeywords);
        model.addAttribute("keywordCount", allKeywords.size());

        return "admin/layout/admin-keyword-list";
    }
}