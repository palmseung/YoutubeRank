package com.palmseung.youtube.domain;

import com.palmseung.youtube.support.YoutubeConstant;

import java.util.Collections;
import java.util.List;

import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_MAX_RESULTS;

public class YoutubeVideos {
    private List<YoutubeVideo> youtubeVideos;

    public YoutubeVideos(List<YoutubeVideo> youtubeVideos) {
        this.youtubeVideos = Collections.unmodifiableList(youtubeVideos);
    }

    public static YoutubeVideos of(List<YoutubeVideo> youtubeVideos){
        return new YoutubeVideos(youtubeVideos);
    }

    private void validateSize(List<YoutubeVideo> youtubeVideos){
        if(youtubeVideos.size() != YOUTUBE_MAX_RESULTS){
            throw new IllegalArgumentException("The number of videos returned should be" + YOUTUBE_MAX_RESULTS);
        }
    }
}
