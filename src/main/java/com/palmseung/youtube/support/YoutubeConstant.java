package com.palmseung.youtube.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YoutubeConstant {
    public static final String BASE_URI_YOUTUBE_API = "/api/youtube";

    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    public static final long YOUTUBE_MAX_RESULTS = 5;
}
