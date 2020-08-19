package com.palmseung.modules.admin.service;

import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.keywords.service.KeywordService;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static com.palmseung.modules.keywords.KeywordConstant.TEST_KEYWORD;
import static com.palmseung.modules.keywords.KeywordConstant.TEST_KEYWORD_2;
import static com.palmseung.modules.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {AdminQueryService.class})
public class AdminQueryServiceTest {
    @Autowired
    private AdminQueryService adminQueryService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private KeywordService keywordService;

    @DisplayName("어드민 계정 생성")
    @Test
    void createAdminAccount() {
        //given
        given(memberService.create(any())).willReturn(TEST_MEMBER);

        //when
        AdminMemberResponseView admin = adminQueryService.createAdmin(TEST_MEMBER);

        //then
        assertThat(admin.getId()).isEqualTo(TEST_ID);
        assertThat(admin.getEmail()).isEqualTo(TEST_EMAIL);
    }

    @DisplayName("회원 목록 조회")
    @Test
    void getAllMembers() {
        //given
        given(memberService.findAll()).willReturn(Arrays.asList(TEST_MEMBER));

        //when
        List<AdminMemberResponseView> allMembers = adminQueryService.getAllMembers();

        //then
        assertThat(allMembers).hasSize(1);
        assertThat(allMembers.get(0).getEmail()).isEqualTo(TEST_EMAIL);
    }

    @DisplayName("등록된 전체 키워드 목록 조회")
    @Test
    void getAllKeywords() {
        //given
        given(keywordService.findAll()).willReturn(Arrays.asList(TEST_KEYWORD, TEST_KEYWORD_2));

        //when
        List<KeywordResponseView> allKeywords = adminQueryService.getAllKeywords();

        //then
        assertThat(allKeywords).hasSize(2);
    }
}