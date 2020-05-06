package com.palmseung.youtube.domain;

import lombok.Getter;

@Getter
public class YoutubeVideo {
    private String videoId;
    private String url;
    private String title;
    private Long viewCount;
    private String thumbnail;
    private String description;
}
