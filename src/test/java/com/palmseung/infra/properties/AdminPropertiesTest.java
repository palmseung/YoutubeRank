package com.palmseung.infra.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminPropertiesTest {
    @Autowired
    private AdminProperties adminProperties;

    @DisplayName("프로퍼티 파일에서 관리자 가입 허용 이메일/비밀번호 불러오기")
    @Test
    public void readAdminProperties() {
        assertThat(adminProperties.getEmail()).isNotNull();
        assertThat(adminProperties.getPassword()).isNotNull();
    }
}