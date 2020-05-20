package com.palmseung.common;

import com.palmseung.members.domain.Member;
import com.palmseung.members.support.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String indexPage(@LoginUser Member loginUser, Model model){
        model.addAttribute("loginUser", loginUser);
        return "index";
    }
}
