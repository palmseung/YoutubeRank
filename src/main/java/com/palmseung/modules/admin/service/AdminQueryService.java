package com.palmseung.modules.admin.service;

import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.keywords.service.KeywordService;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminQueryService {
    private final MemberService memberService;
    private final KeywordService keywordService;

    public AdminMemberResponseView createAdmin(Member adminMember) {
        Member admin = memberService.create(adminMember);

        return AdminMemberResponseView.of(admin);
    }

    @Transactional(readOnly = true)
    public List<AdminMemberResponseView> getAllMembers() {
        return memberService.findAll().stream()
                .map(member -> AdminMemberResponseView.of(member))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseView> getAllKeywords() {
        return keywordService.findAll().stream()
                .map(keyword -> KeywordResponseView.of(keyword))
                .collect(toList());
    }
}