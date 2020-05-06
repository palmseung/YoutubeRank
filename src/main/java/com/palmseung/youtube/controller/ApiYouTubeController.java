package com.palmseung.youtube.controller;

import com.palmseung.youtube.domain.YouTubeVideos;
import com.palmseung.youtube.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;

@RestController
@RequestMapping(BASE_URI_YOUTUBE_API)
@RequiredArgsConstructor
public class ApiYouTubeController {
    private final YouTubeService youTubeService;

    @GetMapping
    public ResponseEntity search(@RequestParam String search) throws IOException {
        YouTubeVideos searchResults = youTubeService.search(search);

        return ResponseEntity
                .ok()
                .body(searchResults.getYouTubeVideos());
    }
}