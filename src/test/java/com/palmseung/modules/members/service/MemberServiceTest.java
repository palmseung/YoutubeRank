package com.palmseung.modules.members.service;

import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.domain.MemberRepository;
import com.palmseung.modules.members.dto.CreateMemberRequestView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.palmseung.modules.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입 - 정상")
    @Test
    public void create() {
        //given
        CreateMemberRequestView requestView = createRequestView();

        //when, then
        assertThatCode(() -> {
            memberService.create(requestView.toEntity());
        }).doesNotThrowAnyException();
    }

    @DisplayName("회원 가입 - save 메소드 호출")
    @Test
    public void create_save() {
        //given
        CreateMemberRequestView requestView = createRequestView();

        //when
        memberService.create(requestView.toEntity());

        //then
        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("회원 가입 - passwordEncoder 메소드 호출")
    @Test
    public void create_encodePassword() {
        //given
        CreateMemberRequestView requestView = createRequestView();

        //when
        memberService.create(requestView.toEntity());

        //then
        verify(passwordEncoder, times(1))
                .encode(TEST_PASSWORD);
    }

    @DisplayName("회원 가입 - 이미 가입된 이메일 검증")
    @Test
    public void validateEmail() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.create(createRequestView().toEntity());
        });
    }

    @DisplayName("회원 탈퇴 - 정상")
    @Test
    void delete() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when, then
        assertThatCode(() -> {
            memberService.delete(TEST_MEMBER);
        }).doesNotThrowAnyException();
    }

    @DisplayName("회원 탈퇴 - 가입 되지 않은 이메일")
    @Test
    void deleteInvalidEmail() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.delete(TEST_MEMBER);
        });
    }

    @DisplayName("회원 조회 by 이메일 - 정상")
    @Test
    void findByEmail() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when
        Member member = memberService.findByEmail(TEST_EMAIL);

        //then
        assertThat(member).isEqualTo(TEST_MEMBER);
    }

    @DisplayName("회원 조회 by 이메일 - 가입 되지 않은 이메일")
    @Test
    void findByInvalidEmail() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.findByEmail(TEST_EMAIL);
        });
    }

    @DisplayName("회원 조회 by 아이디 - 정상")
    @Test
    void findById() {
        //given
        given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(TEST_MEMBER));

        //when
        Member member = memberService.findById(TEST_ID);

        //then
        assertThat(member).isEqualTo(TEST_MEMBER);
    }

    @DisplayName("회원 조회 by 아이디 - 존재 하지 않는 아이디")
    @Test
    void findByInvalidId() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.findById(TEST_ID);
        });
    }

    @DisplayName("로그인 - 정상")
    @Test
    void login() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        //when
        Member member = memberService.login(TEST_EMAIL, TEST_PASSWORD);

        //then
        assertThat(member.getEmail()).isEqualTo(TEST_EMAIL);
    }

    @DisplayName("로그인 - 비밀 번호 불일치")
    @Test
    void loginWhenInvalidPassword() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when, then
        assertThatThrownBy(() -> {
            memberService.login(TEST_EMAIL, TEST_PASSWORD);
        }).isInstanceOf(UsernameNotFoundException.class);
    }

    @DisplayName("로그인 - 가입 되지 않은 사용자")
    @Test
    void loginWhenInvalidMember() {
        //given
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when, then
        assertThatThrownBy(() -> {
            memberService.login(TEST_EMAIL, TEST_PASSWORD);
        }).isInstanceOf(UsernameNotFoundException.class);
    }

    @DisplayName("사용자 정보 수정 - 정상")
    @Test
    void updateMemberInfo() {
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(TEST_MEMBER));

        //when, then
        assertThatCode(() -> {
            memberService.updateInfo(TEST_MEMBER, TEST_ID, "newPassword");
        }).doesNotThrowAnyException();
    }

    @DisplayName("사용자 정보 수정 - 로그인 유저와 변경 대상 유저 불일치")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void updateMemberInfoWhenLoginUserIsNotSame(Long id) {
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(TEST_MEMBER));
        Member loginUser = Member.builder()
                .id(id)
                .email("newEmail")
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.updateInfo(loginUser, id, "newPassword");
        }).withMessageContaining("authorize");
    }

    private CreateMemberRequestView createRequestView() {
        return CreateMemberRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }
}