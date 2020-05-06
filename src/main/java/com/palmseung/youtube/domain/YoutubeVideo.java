package com.palmseung.youtube.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class YoutubeVideo {
    private String videoId;
    private String url;
    private String title;
    private long viewCount;
    private String thumbnail;
    private String description;

    @Builder
    public YoutubeVideo(String videoId, String url, String title,
                        long viewCount, String thumbnail, String description) {
        this.videoId = videoId;
        this.url = url;
        this.title = title;
        this.viewCount = viewCount;
        this.thumbnail = thumbnail;
        this.description = description;
    }
}
