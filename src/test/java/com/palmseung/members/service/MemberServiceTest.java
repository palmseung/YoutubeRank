package com.palmseung.members.service;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.CreateMemberRequestView;
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
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Commit
public class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MyKeywordRepository myKeywordRepository;

    @Mock
    private KeywordRepository keywordRepository;

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
            memberService.create(requestView);
        }).doesNotThrowAnyException();
    }

    @DisplayName("회원 가입 - save 메소드 호출")
    @Test
    public void create_save() {
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
    public void create_encodePassword() {
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
        assertThat(member.getPassword()).isEqualTo(TEST_PASSWORD);
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
        given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(TEST_MEMBER));
        Member updatedMember = createUpdateMember();

        //when
        memberService.updateInfo(TEST_MEMBER, updatedMember);

        //then
        verify(memberRepository, times(1)).save(TEST_MEMBER);
    }

    @DisplayName("사용자 정보 수정 - 로그인 유저와 변경 대상 유저 불일치")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void updateMemberInfoWhenLoginUserIsNotSame(Long id) {
        //given
        given(memberRepository.findById(1L)).willReturn(Optional.of(TEST_MEMBER));
        Member loginUser = Member.builder()
                .id(id)
                .email("newEmail")
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();

        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> memberService.updateInfo(loginUser, TEST_MEMBER))
                .withMessageContaining("authorize");
    }

    @DisplayName("회원 - 키워드 추가")
    @Test
    void addKeyword() {
        //given
        Keyword keyword = createKeyword("queendom");
        Member member = createMember();
        given(keywordRepository.save(any())).willReturn(keyword);
        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));

        //when
        memberService.addKeyword(member, keyword);

        //when
        assertThat(member.getKeywords()).contains(keyword);
        verify(keywordRepository, times(1)).save(any());
        verify(myKeywordRepository, times(1)).save(any(MyKeyword.class));
    }

    @DisplayName("회원 - 나의 키워드 조회")
    @Test
    void findMyKeyword() {
        //given
        Member member = createMember();
        Keyword keyword = new Keyword(1l, "queendom");
        MyKeyword myKeyword = new MyKeyword(1l, member, keyword);
        given(myKeywordRepository.findById(myKeyword.getId())).willReturn(Optional.of(myKeyword));

        //when
        MyKeyword myKeywordByMyKeywordId
                = memberService.findMyKeywordByMyKeywordId(member, myKeyword.getId());

        //then
        assertThat(myKeywordByMyKeywordId.getId()).isEqualTo(myKeyword.getId());
        assertThat(myKeywordByMyKeywordId.getMember()).isEqualTo(member);
    }

    @DisplayName("회원 - 나의 키워드 조회 (등록 유저와 로그인 유저 불일치)")
    @Test
    void findMyKeywordWhenLoginUserIsNotRegisterUser() {
        //given
        Member member = createMember();
        Member anotherMember = createAnotherMember();
        Keyword keyword = new Keyword(1l, "queendom");
        MyKeyword myKeyword = new MyKeyword(1l, member, keyword);
        given(myKeywordRepository.findById(myKeyword.getId())).willReturn(Optional.of(myKeyword));

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.findMyKeywordByMyKeywordId(anotherMember, myKeyword.getId());
        }).withMessageContaining("authorize");
    }

    @DisplayName("회원 - 나의 키워드 목록 조회")
    @Test
    void findAllMyKeywords() {
        //given
        Member member = createMember();
        Keyword keyword1 = new Keyword(1l, "queendom");
        Keyword keyword2 = new Keyword(2l, "(g)idle");
        MyKeyword myKeyword1 = new MyKeyword(1l, member, keyword1);
        MyKeyword myKeyword2 = new MyKeyword(2l, member, keyword2);
        given(myKeywordRepository.findAllByMemberId(member.getId()))
                .willReturn(Arrays.asList(myKeyword1, myKeyword2));

        //when
        List<MyKeyword> allKeywords = memberService.findAllKeywords(member);

        //then
        assertThat(allKeywords).hasSize(2);
    }

    @DisplayName("회원 - 나의 키워드 삭제")
    @Test
    void deleteMyKeyword() {
        //given
        Member member = createMember();
        Keyword keyword = new Keyword(1l, "queendom");
        MyKeyword myKeyword = new MyKeyword(1l, member, keyword);
        given(myKeywordRepository.findById(1l)).willReturn(Optional.of(myKeyword));

        //when
        memberService.deleteMyKeywordById(member, myKeyword.getId());

        //then
        verify(myKeywordRepository, times(1)).deleteById(myKeyword.getId());
    }

    @DisplayName("회원 - 나의 키워드 삭제 (로그인 유저와 등록 유저 불일치)")
    @Test
    void deleteMyKeywordWhenRegisterUserIsNotLoginUser() {
        //given
        Member member = createMember();
        Member anotherMember = createAnotherMember();
        Keyword keyword = new Keyword(1l, "queendom");
        MyKeyword myKeyword = new MyKeyword(1l, member, keyword);
        given(myKeywordRepository.findById(1l)).willReturn(Optional.of(myKeyword));

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            memberService.deleteMyKeywordById(anotherMember, myKeyword.getId());
        }).withMessageContaining("authorize");
    }

    private Member createAnotherMember() {
        return new Member(2L, "shu@email.com", "shu", "password", Arrays.asList("ROLE_USER"));
    }

    private Member createMember() {
        return new Member(1L, "sjsj@email.com", "soojin", "password", Arrays.asList("ROLE_USER"));
    }

    private Keyword createKeyword(String keyword) {
        return new Keyword(1l, keyword);
    }

    private Member createUpdateMember() {
        return Member.builder()
                .id(TEST_ID)
                .email(TEST_EMAIL)
                .name("newName")
                .password("newPassword")
                .build();
    }

    private CreateMemberRequestView createRequestView() {
        return CreateMemberRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }
}