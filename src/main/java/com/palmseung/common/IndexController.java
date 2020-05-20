package com.palmseung.common;

import com.palmseung.members.domain.Member;
import com.palmseung.members.support.LoginUser;
import com.palmseung.youtube.domain.YouTubeVideos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String indexPage(@LoginUser Member loginUser, Model model, @ModelAttribute YouTubeVideos youTubeVideos,
                            HttpServletRequest request){
        YouTubeVideos youTubeVideos1 = (YouTubeVideos) request.getAttribute("youTubeVideos");

        model.addAttribute("loginUser", loginUser);
        if(youTubeVideos != null){
            model.addAttribute("youTubeVideos", youTubeVideos.getYouTubeVideos());
        }

        return "index";
    }

    @RequestMapping("/search")
    public String search(@LoginUser Member loginUser, Model model,
                         @RequestParam String keyword){
        model.addAttribute("loginUser", loginUser);
//        if(loginUser != null){
//
//        }
        return "redirect:/api/keywords";
    }

    @RequestMapping("/search-result")
    public String searchResult(@LoginUser Member loginUser, Model model,
                               @ModelAttribute("youTubeVideos") YouTubeVideos youTubeVideos){
        YouTubeVideos youTubeVideos1 = youTubeVideos;

        model.addAttribute("youTubeVideos", youTubeVideos1.getYouTubeVideos());
        model.addAttribute("loginUser", loginUser);

        return "index";
    }

}
