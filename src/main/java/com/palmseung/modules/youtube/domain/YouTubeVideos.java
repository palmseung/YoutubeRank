package com.palmseung.modules.youtube.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.palmseung.modules.youtube.YoutubeConstant.YOUTUBE_NUMBER_OF_RESULT;

@Getter
public class YouTubeVideos {
    private List<YouTubeVideo> youTubeVideos;

    public YouTubeVideos(List<YouTubeVideo> youTubeVideos) {
        validateSize(youTubeVideos);
        validateViewCountRule(youTubeVideos);
        this.youTubeVideos = Collections.unmodifiableList(youTubeVideos);
    }

    public static YouTubeVideos of(List<YouTubeVideo> youTubeVideos) {
        return new YouTubeVideos(youTubeVideos);
    }

    private void validateViewCountRule(List<YouTubeVideo> videos) {
        boolean followViewCountRule = IntStream.range(0, videos.size() - 1)
                .allMatch(i -> videos.get(i).getViewCount() >= videos.get(i + 1).getViewCount());

        if (followViewCountRule == false) {
            throw new IllegalArgumentException("View counts should be in descending order!");
        }
    }

    private void validateSize(List<YouTubeVideo> youTubeVideos) {
        if (youTubeVideos.size() != YOUTUBE_NUMBER_OF_RESULT) {
            throw new IllegalArgumentException("The number of videos returned should be" + YOUTUBE_NUMBER_OF_RESULT);
        }
    }
}