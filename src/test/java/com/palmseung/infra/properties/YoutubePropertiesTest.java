package com.palmseung.infra.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class YoutubePropertiesTest {
    @Autowired
    private YoutubeProperties youtubeProperties;

    @DisplayName("Youtube api-key 읽어 오기")
    @Test
    void read() {
        assertThat(youtubeProperties.getApiKey()).isNotNull();
    }
}