package com.palmseung.members.controller;

import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.LoginUser;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/sign-up")
    public String signUpPage(){
        return "member/sign-up";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "member/login";
    }

    @GetMapping("/my-info")
    public String myInfoPage(Model model, @LoginUser Member loginUser){
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
            return "member/my-info";
        }

        return "member/login";
//        return "member/my-info";
    }
}
