package com.palmseung.modules.admin.service;

import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private MemberService memberService;

    @DisplayName("어드민 계정 생성 요청 시, memberService의 create 메소드가 호출된다.")
    @Test
    void createAdminAccount() {
        //when
        adminService.createAdmin(TEST_MEMBER);

        //then
        verify(memberService, times(1)).create(any(Member.class));
    }
}