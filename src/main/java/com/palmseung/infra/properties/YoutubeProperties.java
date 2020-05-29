package com.palmseung.infra.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "youtube")
public class YoutubeProperties {
    private String apiKey;

    public YoutubeProperties(String apiKey) {
        this.apiKey = apiKey;
    }
}