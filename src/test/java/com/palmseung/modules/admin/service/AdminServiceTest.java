package com.palmseung.modules.admin.service;

import com.palmseung.BaseContainerTest;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest extends BaseContainerTest {
    @InjectMocks
    private AdminService adminService;

    @MockBean
    private MemberService memberService;

    @DisplayName("어드민 계정 생성 요청 시, memberService의 create 메소드가 호출된다.")
    @Test
    void createAdminAccount() {
        //given
        BDDMockito.given(memberService.create(any(Member.class))).willReturn(TEST_MEMBER);

        //when
        AdminMemberResponseView admin = adminService.createAdmin(TEST_MEMBER);

        //then
        assertThat(admin.getId()).isEqualTo(TEST_MEMBER.getId());
    }
}