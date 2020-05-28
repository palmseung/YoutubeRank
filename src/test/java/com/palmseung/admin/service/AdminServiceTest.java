package com.palmseung.admin.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("어드민 계정 생성 요청 시, 회원 저장 레포지토리 메소드가 호출된다.")
    @Test
    void createAdminAccount() {
        //when
        adminService.createAdmin(TEST_MEMBER);

        //then
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}