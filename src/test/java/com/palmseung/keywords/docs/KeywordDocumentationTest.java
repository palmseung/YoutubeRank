package com.palmseung.keywords.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.keywords.service.KeywordService;
import com.palmseung.members.domain.Member;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.JwtTokenProvider;
import com.palmseung.members.support.UserMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.palmseung.keywords.KeywordConstant.*;
import static com.palmseung.members.MemberConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KeywordDocumentationTest extends BaseDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MemberService memberService;

    @MockBean
    private KeywordService keywordService;

    @DisplayName("[문서화] My Keyword 추가")
    @Test
    public void addMyKeyword() throws Exception {
        //given
        String keyword = "queendom";
        Member member = createMember(TEST_EMAIL);
        MyKeywordRequestView requestView = MyKeywordRequestView.builder().keyword(keyword).build();
        given(memberService.addKeyword(any(), any())).willReturn(TEST_MY_KEYWORD);
        given(memberService.findByEmail(TEST_EMAIL)).willReturn(member);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(member));

        //when, then
        mockMvc.perform(post(BASE_URI_KEYWORD_API)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestView)))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andDo(document("keywords-add-my-keyword",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        ),
                        requestFields(
                                fieldWithPath("keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword to search in youtube that the user wants to add my keyword")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION)
                                        .description("The url for redirect to search on YouTube")
                        )
                ));
    }

    @DisplayName("[문서화] My Keyword 조회")
    @Test
    public void retrieveMyKeyword() throws Exception {
        //given
        Member loginUser = createMember(TEST_EMAIL);
        String keyword = TEST_KEYWORD.getKeyword();
        given(keywordService.findMyKeyword(loginUser, keyword)).willReturn(TEST_MY_KEYWORD);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(loginUser));

        //when, then
        mockMvc.perform(get(BASE_URI_KEYWORD_API+ "/" + keyword)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("keywords-retrieve-my-keyword",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The intrinsic myKeyword id to retrieve"),
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("The email address of an user who added the keyword"),
                                fieldWithPath("keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword added to loginUser's my-keyword to retrieve")
                        )
                ));
    }

    @DisplayName("[문서화] My Keyword 목록 조회")
    @Test
    public void retrieveAllMyKeywords() throws Exception {
        //given
        Member loginUser = createMember(TEST_EMAIL);
        given(keywordService.findAllMyKeyword(loginUser))
                .willReturn(Arrays.asList(TEST_MY_KEYWORD, TEST_MY_KEYWORD_2, TEST_MY_KEYWORD_3));
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(loginUser));

        //when, then
        mockMvc.perform(get(BASE_URI_KEYWORD_API)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("keywords-retrieve-all-my-keywords",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The intrinsic myKeyword id to retrieve"),
                                fieldWithPath("[].email")
                                        .type(JsonFieldType.STRING)
                                        .description("The email address of an user who added the keyword"),
                                fieldWithPath("[].keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword added to loginUser's my-keyword to retrieve")
                        )
                ));
    }

    @DisplayName("[문서화] My Keyword 삭제")
    @Test
    public void deleteMyKeyword() throws Exception {
        //given
        String keyword = TEST_KEYWORD.getKeyword();
        Member loginUser = createMember(TEST_EMAIL);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(loginUser));

        //when, then
        mockMvc.perform(delete(BASE_URI_KEYWORD_API + "/" + keyword)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("keywords-delete-my-keyword",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        )
                ));
    }

    private String createToken(String email) {
        return jwtTokenProvider.createToken(TEST_EMAIL);
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