package com.palmseung.modules.admin.service;

import com.palmseung.BaseContainerTest;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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
}