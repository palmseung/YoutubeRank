package com.palmseung.members.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/sign-up")
    public String signUpPage(){
        return "member/sign-up";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "member/login";
    }
}
