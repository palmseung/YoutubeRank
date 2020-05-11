package com.palmseung.members.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.JwtTokenProvider;
import com.palmseung.members.support.UserMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.palmseung.members.MemberConstant.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원 가입 페이지 출력")
    @Test
    void signUpPage() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/sign-up"));
    }

    @DisplayName("로그인 페이지 출력")
    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"));
    }

    @DisplayName("인증된 회원 - 회원 정보 조회(+업데이트) 페이지 출력")
    @Test
    void myInfoPage() throws Exception {
        //given
        Member member = createMember(TEST_EMAIL);
        String token = jwtTokenProvider.createToken(TEST_EMAIL);
        given(memberService.findByEmail(TEST_EMAIL)).willReturn(member);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(member));

        //when, then
        mockMvc.perform(get("/my-info")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(view().name("member/my-info"));
    }

    @DisplayName("비인증 회원 - 회원 정보 조회 요청 시, 로그인 페이지 출력")
    @Test
    void myInfoPageWhenInvalidMember() throws Exception {
        //when, then
        mockMvc.perform(get("/my-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"));
    }

    private Member createMember(String email) {
        return Member.builder()
                .id(TEST_ID)
                .email(email)
                .name(TEST_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }
}