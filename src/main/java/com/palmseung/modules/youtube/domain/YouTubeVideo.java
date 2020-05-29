package com.palmseung.modules.youtube.domain;

import com.google.api.services.youtube.model.SearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.palmseung.modules.youtube.YoutubeConstant.YOUTUBE_URL;

@NoArgsConstructor
@Getter
public class YouTubeVideo {
    private String videoId;
    private String url;
    private String title;
    private String thumbnailUrl;
    private String description;
    private long viewCount;

    @Builder
    public YouTubeVideo(String videoId, String url, String title, long viewCount,
                        String thumbnailUrl, String description) {
        this.videoId = videoId;
        this.url = url;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.viewCount = viewCount;
    }

    public static YouTubeVideo of(SearchResult result, long viewCount) {
        return YouTubeVideo.builder()
                .videoId(result.getId().getVideoId())
                .url(YOUTUBE_URL + result.getId().getVideoId())
                .title(result.getSnippet().getTitle())
                .description(result.getSnippet().getDescription())
                .thumbnailUrl(result.getSnippet().getThumbnails().getHigh().getUrl())
                .viewCount(viewCount)
                .build();
    }
}