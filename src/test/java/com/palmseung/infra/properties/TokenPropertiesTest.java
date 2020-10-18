package com.palmseung.infra.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenPropertiesTest {
    @Autowired
    private TokenProperties tokenProperties;

    @DisplayName("프로퍼티 파일에서 secretKey, expireLength 불러오기")
    @Test
    void readProperties() {
        assertThat(tokenProperties.getExpireLength()).isNotNull();
        assertThat(tokenProperties.getSecretKey()).isNotNull();
    }
}
