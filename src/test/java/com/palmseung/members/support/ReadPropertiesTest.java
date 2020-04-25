package com.palmseung.members.support;

import com.palmseung.support.ReadProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ReadProperties.class)
public class ReadPropertiesTest {
    @Autowired
    private ReadProperties readProperties;

    @DisplayName("yml 파일에서 secretKey, expireLength 불러오기")
    @Test
    void readProperties() {
        assertThat(readProperties.getExpireLength()).isNotNull();
        assertThat(readProperties.getSecretKey()).isNotNull();
    }
}
