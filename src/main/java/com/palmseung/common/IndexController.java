package com.palmseung.common;

import com.palmseung.members.domain.Member;
import com.palmseung.members.support.LoginUser;
import com.palmseung.youtube.domain.YouTubeVideos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String indexPage(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);

        return "index";
    }

    @RequestMapping("/search-result")
    public String searchResult(@LoginUser Member loginUser, Model model,
                               @ModelAttribute("youTubeVideos") YouTubeVideos youTubeVideos) {
        model.addAttribute("youTubeVideos", youTubeVideos.getYouTubeVideos());
        model.addAttribute("loginUser", loginUser);

        return "index";
    }

}
