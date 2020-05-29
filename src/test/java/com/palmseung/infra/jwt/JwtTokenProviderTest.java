package com.palmseung.infra.jwt;

import com.palmseung.modules.members.UserMember;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.domain.MemberRepository;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static com.palmseung.modules.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @DisplayName("Jwt - Access 토큰 생성")
    @Test
    void createToken() {
        //when
        String token = jwtTokenProvider.createToken(TEST_EMAIL);

        //then
        assertThat(token).isNotEmpty();
    }

    @DisplayName("Jwt - 토큰에서 email 추출")
    @Test
    void extractEmail() {
        //given
        String token = jwtTokenProvider.createToken(TEST_EMAIL);

        //when
        String email = jwtTokenProvider.extractEmail(token);

        //then
        assertThat(email).isEqualTo(TEST_EMAIL);
    }

    @DisplayName("Jwt - 유효성 검증 (유효한 토큰)")
    @Test
    public void validateTokenValidityWhenValid() {
        //given
        String token = jwtTokenProvider.createToken(TEST_EMAIL);

        //when
        boolean isValidToken = jwtTokenProvider.isValidToken(token);

        //then
        assertThat(isValidToken).isTrue();
    }

    @DisplayName("Jwt - 유효성 검증 (유효하지 않은 토큰)")
    @Test
    public void validateTokenValidityWhenInvalid() {
        //when
        boolean isValidToken = jwtTokenProvider.isValidToken(TEST_EMAIL);

        //then
        assertThat(isValidToken).isFalse();
    }

    @DisplayName("Jwt - 토큰에서 Authentication 추출")
    @Test
    public void extractAuthentication() {
        //given
        String token = jwtTokenProvider.createToken(TEST_EMAIL);
        UserMember userMember = createUserMember();
        when(memberService.findByEmail(anyString())).thenReturn(TEST_MEMBER);
        when(memberService.loadUserByUsername(anyString())).thenReturn((UserDetails) userMember);

        //when
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        //then
        UserMember userMemberInToken = (UserMember) authentication.getPrincipal();
        Member memberInToken = userMemberInToken.getMember();
        assertThat(memberInToken.getEmail()).isEqualTo(TEST_EMAIL);
    }

    private UserMember createUserMember() {
        return new UserMember(createMember(TEST_EMAIL));
    }

    private Member createMember(String email) {
        return Member.builder()
                .id(TEST_ID)
                .email(email)
                .name(TEST_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }
}