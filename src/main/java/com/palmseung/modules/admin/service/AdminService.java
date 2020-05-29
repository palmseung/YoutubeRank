package com.palmseung.modules.admin.service;

import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.keywords.service.KeywordService;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private MemberService memberService;
    private KeywordService keywordService;

    public AdminService(MemberService memberService, KeywordService keywordService) {
        this.memberService = memberService;
        this.keywordService = keywordService;
    }

    public AdminMemberResponseView createAdmin(Member adminMember) {
        Member admin = memberService.create(adminMember);
        return AdminMemberResponseView.of(admin);
    }

    @Transactional(readOnly = true)
    public List<AdminMemberResponseView> getAllMembers() {
        return memberService.findAll().stream()
                .map(m -> AdminMemberResponseView.of(m))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseView> getAllKeywords() {
        List<Keyword> all = keywordService.findAll();

        return all.stream()
                .map(k -> new KeywordResponseView(k.getId(), k.getKeyword()))
                .collect(Collectors.toList());
    }
}