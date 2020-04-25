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
}