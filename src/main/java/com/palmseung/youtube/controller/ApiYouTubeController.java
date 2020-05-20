package com.palmseung.youtube.controller;

import com.palmseung.youtube.domain.YouTubeVideos;
import com.palmseung.youtube.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.TEST_YOUTUBE_VIDEOS;

@Controller
@RequestMapping(BASE_URI_YOUTUBE_API)
@RequiredArgsConstructor
public class ApiYouTubeController {
    private final YouTubeService youTubeService;

    @GetMapping
    public String search(@RequestParam String keyword, RedirectAttributes redirect) throws IOException {
        YouTubeVideos searchResults = youTubeService.search(keyword);
//        YouTubeVideos searchResults = TEST_YOUTUBE_VIDEOS;
        redirect.addFlashAttribute("youTubeVideos", searchResults);
        return "redirect:/search-result";
    }
}