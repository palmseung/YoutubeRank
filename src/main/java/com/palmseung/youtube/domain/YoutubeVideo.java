package com.palmseung.youtube.domain;

import com.google.api.services.youtube.model.SearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_URL;

@NoArgsConstructor
@Getter
public class YoutubeVideo {
    private String videoId;
    private String url;
    private String title;
    private String thumbnailUrl;
    private String description;
    private long viewCount;

    @Builder
    public YoutubeVideo(String videoId, String url, String title, long viewCount,
                        String thumbnailUrl, String description) {
        this.videoId = videoId;
        this.url = url;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.viewCount = viewCount;
    }

    public static YoutubeVideo of(SearchResult result, long viewCount) {
        return YoutubeVideo.builder()
                .videoId(result.getId().getVideoId())
                .url(YOUTUBE_URL + result.getId().getVideoId())
                .title(result.getSnippet().getTitle())
                .description(result.getSnippet().getDescription())
                .thumbnailUrl(result.getSnippet().getThumbnails().getHigh().getUrl())
                .viewCount(viewCount)
                .build();
    }
}