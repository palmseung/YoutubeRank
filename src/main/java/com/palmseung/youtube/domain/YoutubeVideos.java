package com.palmseung.youtube.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_MAX_RESULTS;

public class YoutubeVideos {
    private List<YoutubeVideo> youtubeVideos;

    public YoutubeVideos(List<YoutubeVideo> youtubeVideos) {
        validateSize(youtubeVideos);
        validateViewCountRule(youtubeVideos);
        this.youtubeVideos = Collections.unmodifiableList(youtubeVideos);
    }

    public static YoutubeVideos of(List<YoutubeVideo> youtubeVideos) {
        return new YoutubeVideos(youtubeVideos);
    }

    private void validateViewCountRule(List<YoutubeVideo> videos) {
        boolean followViewCountRule = IntStream.range(0, videos.size() - 1)
                .allMatch(i -> videos.get(i).getViewCount() >= videos.get(i + 1).getViewCount());

        if (followViewCountRule == false) {
            throw new IllegalArgumentException("View counts should be in descending order!");
        }
    }

    private void validateSize(List<YoutubeVideo> youtubeVideos) {
        if (youtubeVideos.size() != YOUTUBE_MAX_RESULTS) {
            throw new IllegalArgumentException("The number of videos returned should be" + YOUTUBE_MAX_RESULTS);
        }
    }
}