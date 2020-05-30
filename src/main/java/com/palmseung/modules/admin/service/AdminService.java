package com.palmseung.modules.admin.service;

import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminQueryService adminQueryService;

    public AdminMemberResponseView createAdmin(Member adminMember) {
        return adminQueryService.createAdmin(adminMember);
    }

    @Transactional(readOnly = true)
    public List<AdminMemberResponseView> getAllMembers() {
        return adminQueryService.getAllMembers();
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseView> getAllKeywords() {
        return adminQueryService.getAllKeywords();
    }
}