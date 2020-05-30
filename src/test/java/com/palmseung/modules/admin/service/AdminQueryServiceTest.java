package com.palmseung.modules.admin.service;

import com.palmseung.BaseContainerTest;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static com.palmseung.modules.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class AdminQueryServiceTest extends BaseContainerTest {
    @Autowired
    private AdminQueryService adminQueryService;

    @MockBean
    private MemberService memberService;

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
}