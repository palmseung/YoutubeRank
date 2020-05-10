package com.palmseung.members.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/sign-up")
    public String signUpPage(Model model){
        return "member/sign-up";
    }
}
