package com.palmseung.members.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.CreateMemberRequestView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.palmseung.members.MemberConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("create메소드가 호출되면, userRepository의 save 메소드가 1번 호출된다.")
    @Test
    public void create() {
        //given
        CreateMemberRequestView requestView = createRequestView();

        //when
        memberService.create(requestView);

        //then
        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("create 메소드가 호출되면, Passwornd를 encoding하는 메소드가 호출된다.")
    @Test
    public void encodePassword() {
        //given
        CreateMemberRequestView requestView = createRequestView();

        //when
        memberService.create(requestView);

        //then
        verify(passwordEncoder, times(1))
                .encode(TEST_PASSWORD);
    }

    private CreateMemberRequestView createRequestView() {
        return CreateMemberRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }
}