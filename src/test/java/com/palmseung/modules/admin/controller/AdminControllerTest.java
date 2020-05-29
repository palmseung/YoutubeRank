package com.palmseung.modules.admin.controller;

import com.palmseung.modules.keywords.domain.KeywordRepository;
import com.palmseung.modules.members.domain.MemberRepository;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.palmseung.modules.keywords.KeywordConstant.TEST_KEYWORD;
import static com.palmseung.modules.keywords.KeywordConstant.TEST_KEYWORD_2;
import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private KeywordRepository keywordRepository;

    @DisplayName("어드민 멤버 리스트 페이지 출력")
    @Test
    void adminMemberPortion() throws Exception {
        //given
        given(memberRepository.findAll()).willReturn(Arrays.asList(TEST_MEMBER));

        //when, then
        mockMvc.perform(get("/admin/members"))
                .andExpect(model().attributeExists("allMembers"))
                .andExpect(model().attribute("memberCount", 1))
                .andExpect(view().name("admin/layout/admin-member-list"));
    }

    @DisplayName("어드민 키워드 리스트 페이지 출력")
    @Test
    void adminKeywordPortion() throws Exception {
        //given
        given(keywordRepository.findAll()).willReturn(Arrays.asList(TEST_KEYWORD, TEST_KEYWORD_2));

        mockMvc.perform(get("/admin/keywords"))
                .andExpect(model().attributeExists("allKeywords"))
                .andExpect(model().attribute("keywordCount", 2))
                .andExpect(view().name("admin/layout/admin-keyword-list"));
    }
}