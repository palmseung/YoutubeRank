package com.palmseung.modules.admin.service;

import com.palmseung.BaseContainerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AdminServiceTest extends BaseContainerTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminQueryService adminQueryService;


    @DisplayName("어드민 계정 생성 요청 시, memberQueryService의 create 메소드가 호출된다.")
    @Test
    void createAdminAccount() {
        //when
        adminService.createAdmin(TEST_MEMBER);

        //then
        verify(adminQueryService, times(1)).createAdmin(TEST_MEMBER);
    }
}