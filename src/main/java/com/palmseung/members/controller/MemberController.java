package com.palmseung.members.controller;

import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String index(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        return "index";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        return "member/sign-up";
    }

    @GetMapping("/login")
    public String loginPage(@LoginUser Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        return "member/login";
    }

    @GetMapping("/my-info")
    public String myInfoPage(Model model, @LoginUser Member loginUser) {
        model.addAttribute("loginUser", loginUser);

        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
            return "member/my-info";
        }

        return "member/login";
    }
}