package com.palmseung.members.support;

import com.palmseung.support.jwt.ReadProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReadPropertiesTest {
    @Autowired
    private ReadProperties readProperties;

    @DisplayName("application.properties 파일에서 secretKey, expireLength 불러오기")
    @Test
    void readProperties() {
        assertThat(readProperties.getExpireLength()).isNotNull();
        assertThat(readProperties.getSecretKey()).isNotNull();
    }
}
