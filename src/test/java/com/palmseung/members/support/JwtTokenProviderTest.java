package com.palmseung.members.support;

import com.palmseung.support.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.palmseung.members.MemberConstant.TEST_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JwtTokenProvider.class)
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
        //given
        String token = TEST_EMAIL;

        //when
        boolean isValidToken = jwtTokenProvider.isValidToken(token);

        //then
        assertThat(isValidToken).isFalse();
    }
}