package com.palmseung.support;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "token")
public class ReadProperties {
    private String secretKey;
    private Long expireLength;
}
