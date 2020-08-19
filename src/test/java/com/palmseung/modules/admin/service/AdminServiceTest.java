package com.palmseung.modules.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
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