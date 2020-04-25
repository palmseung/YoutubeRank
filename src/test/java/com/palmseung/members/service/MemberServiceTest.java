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

import java.util.Optional;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @DisplayName("회원 가입 - save 메소드 호출")
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

    @DisplayName("회원 가입 - passwordEncoder 메소드 호출")
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

    @DisplayName("회원 가입 - 이미 존재 하는 이메일 검증")
    @Test
    public void validateEmail() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.create(createRequestView());
        });
    }

    private CreateMemberRequestView createRequestView() {
        return CreateMemberRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }
}