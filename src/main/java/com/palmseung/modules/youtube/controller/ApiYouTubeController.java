package com.palmseung.modules.youtube.controller;

import com.palmseung.modules.youtube.domain.YouTubeVideo;
import com.palmseung.modules.youtube.domain.YouTubeVideos;
import com.palmseung.modules.youtube.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.palmseung.modules.youtube.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.modules.youtube.YoutubeConstant.TEST_YOUTUBE_VIDEOS;

@RestController
@RequestMapping(BASE_URI_YOUTUBE_API)
@RequiredArgsConstructor
public class ApiYouTubeController {
    private final YouTubeService youTubeService;

    @GetMapping
    public ResponseEntity search(@RequestParam String keyword) throws IOException {
        YouTubeVideos searchResults = youTubeService.search(keyword);
        List<YouTubeVideo> youTubeVideos = searchResults.getYouTubeVideos();

        return ResponseEntity
                .ok()
                .body(youTubeVideos);
    }
}