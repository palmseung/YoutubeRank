package com.palmseung.youtube.support;

import lombok.Getter;

@Getter
public class YoutubeProperties {
    private String apiKey;

    public YoutubeProperties(String apiKey) {
        this.apiKey = apiKey;
    }
}
