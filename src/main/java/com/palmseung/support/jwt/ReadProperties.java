package com.palmseung.support.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "token")
public class ReadProperties {
    private String secretKey;
    private Long expireLength;

    public ReadProperties(String secretKey, Long expireLength) {
        this.secretKey = secretKey;
        this.expireLength = expireLength;
    }
}
