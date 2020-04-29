package com.palmseung.members.support;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.support.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.palmseung.members.MemberConstant.TEST_EMAIL;
import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    MemberRepository memberRepository;

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
        assertThatIllegalArgumentException()
                .isThrownBy(() -> jwtTokenProvider.isValidToken(TEST_EMAIL))
                .withMessageContaining("token");
    }

    @DisplayName("Jwt - 토큰에서 Authentication 추출")
    @Test
    public void extractAuthentication() {
        //given
        String token = jwtTokenProvider.createToken(TEST_EMAIL);
        given(memberRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(TEST_MEMBER));

        //when
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        //then
        Member memberInToken = (Member) authentication.getPrincipal();
        assertThat(memberInToken.getEmail()).isEqualTo(TEST_EMAIL);
    }
}