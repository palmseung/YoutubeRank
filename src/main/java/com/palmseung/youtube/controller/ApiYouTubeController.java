package com.palmseung.youtube.controller;

import com.palmseung.members.domain.Member;
import com.palmseung.members.support.LoginUser;
import com.palmseung.youtube.domain.YouTubeVideos;
import com.palmseung.youtube.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.TEST_YOUTUBE_VIDEOS;

@Controller
@RequestMapping(BASE_URI_YOUTUBE_API)
@RequiredArgsConstructor
public class ApiYouTubeController {
    private final YouTubeService youTubeService;

    @GetMapping
    public String search(@RequestParam String keyword, Model model, RedirectAttributes redirect, HttpServletRequest request, @LoginUser Member loginUser) throws IOException {
//        YouTubeVideos youTubeVideos = youTubeService.search(keyword);
        YouTubeVideos youTubeVideos = TEST_YOUTUBE_VIDEOS;
//        return ResponseEntity
//                .ok()
//                .body(youTubeVideos.getYouTubeVideos());
//        return ResponseEntity
//                .status(HttpStatus.MOVED_PERMANENTLY)
//                .header(HttpHeaders.LOCATION, "/")
//                .body(youTubeVideos.getYouTubeVideos());
//        model.addAttribute("youTubeVideos", youTubeVideos);
//        model.addAttribute("loginUser", loginUser);

//        redirect.addAttribute("youTubeVideos", youTubeVideos);
//        redirect.addAttribute(youTubeVideos);
        redirect.addFlashAttribute("youTubeVideos", youTubeVideos);


//        request.setAttribute("youTubeVideos", youTubeVideos.getYouTubeVideos());
//        request.setAttribute("loginUser", loginUser);
        return "redirect:/search-result";
    }
}