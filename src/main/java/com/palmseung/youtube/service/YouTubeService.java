package com.palmseung.youtube.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.palmseung.youtube.domain.YoutubeVideo;
import com.palmseung.youtube.domain.YoutubeVideos;
import com.palmseung.youtube.support.YoutubeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.palmseung.youtube.support.YoutubeConstant.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class YouTubeService {
    private static ResourceBundle propertiesBundle;
    private static YouTube youTube;

    private final YoutubeProperties youtubeProperties;

    static {
        propertiesBundle = ResourceBundle.getBundle(APPLICATION_PROPERTIES);
        youTube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName(YOUTUBE_API_APPLICATION).build();
    }

    public YoutubeVideos search(String searchKeyword) throws IOException {
        String apiKey = youtubeProperties.getApiKey();

        return executeSearching(searchKeyword, apiKey)
                .stream()
                .map(searchResult -> YoutubeVideo.of(searchResult, getViewCount(searchResult, apiKey)))
                .collect(Collectors.collectingAndThen(toList(), YoutubeVideos::new));
    }

    private List<SearchResult> executeSearching(String keyword, String apiKey) throws IOException {
        return youTube
                .search()
                .list(YOUTUBE_PART_FOR_SEARCH)
                .setKey(apiKey)
                .setQ(keyword)
                .setOrder(YOUTUBE_VIEW_COUNT)
                .setType(YOUTUBE_SEARCH_TYPE)
                .setFields(YOUTUBE_SEARCH_FIELDS)
                .setMaxResults(YOUTUBE_NUMBER_OF_RESULT)
                .execute()
                .getItems();
    }

    private long getViewCount(SearchResult searchResult, String apiKey) {
        long viewCount = 0;

        try {
            viewCount = youTube
                    .videos()
                    .list(YOUTUBE_PART_FOR_STATISTICS)
                    .setKey(apiKey)
                    .setId(String.valueOf(searchResult.getId().getVideoId()))
                    .execute()
                    .getItems()
                    .stream()
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("ViewCount Result Not Found result"))
                    .getStatistics()
                    .getViewCount()
                    .longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return viewCount;
    }
}