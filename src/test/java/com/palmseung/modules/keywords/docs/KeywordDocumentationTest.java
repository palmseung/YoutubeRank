package com.palmseung.modules.keywords.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.infra.jwt.JwtTokenProvider;
import com.palmseung.modules.keywords.dto.MyKeywordRequestView;
import com.palmseung.modules.keywords.service.KeywordService;
import com.palmseung.modules.members.UserMember;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.service.MemberService;
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

import static com.palmseung.modules.keywords.KeywordConstant.*;
import static com.palmseung.modules.members.MemberConstant.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
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
        given(keywordService.addMyKeyword(member, keyword)).willReturn(TEST_MY_KEYWORD);
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
                        links(halLinks(),
                                linkWithRel("self")
                                        .description("link to self"),
                                linkWithRel("profile")
                                        .description("link to profile"),
                                linkWithRel("delete-my-keyword")
                                        .description("link to delete MyKeyword"),
                                linkWithRel("retrieve-my-keyword")
                                        .description("link to retrieve MyKeyword")
                        ),
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
                                        .description("The keyword added to loginUser's my-keyword to retrieve"),
                                fieldWithPath("_links.self.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to self"),
                                fieldWithPath("_links.profile.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to profile"),
                                fieldWithPath("_links.delete-my-keyword.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to delete MyKeyword"),
                                fieldWithPath("_links.retrieve-my-keyword.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to retrieve MyKeyword")
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
        mockMvc.perform(get(BASE_URI_KEYWORD_API + "/" + keyword)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("keywords-retrieve-my-keyword",
                        links(halLinks(),
                                linkWithRel("self")
                                        .description("link to self"),
                                linkWithRel("profile")
                                        .description("link to profile"),
                                linkWithRel("delete-my-keyword")
                                        .description("link to delete MyKeyword")
                        ),
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
                                        .description("The keyword added to loginUser's my-keyword to retrieve"),
                                fieldWithPath("_links.self.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to self"),
                                fieldWithPath("_links.profile.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to profile"),
                                fieldWithPath("_links.delete-my-keyword.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to delete MyKeyword")
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
                        links(halLinks(),
                                linkWithRel("self")
                                        .description("link to self"),
                                linkWithRel("profile")
                                        .description("link to profile")
                        ),
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
                                fieldWithPath("myKeywords[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The intrinsic myKeyword id to retrieve"),
                                fieldWithPath("myKeywords[].email")
                                        .type(JsonFieldType.STRING)
                                        .description("The email address of an user who added the keyword"),
                                fieldWithPath("myKeywords[].keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword added to loginUser's my-keyword to retrieve"),
                                fieldWithPath("_links.self.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to self"),
                                fieldWithPath("_links.profile.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to profile")
                        )
                ));
    }

    @DisplayName("[문서화] My Keyword 삭제")
    @Test
    public void deleteMyKeyword() throws Exception {
        //given
        String keyword = TEST_KEYWORD.getKeyword();
        Member loginUser = createMember(TEST_EMAIL);
        given(keywordService.findMyKeyword(loginUser, keyword)).willReturn(TEST_MY_KEYWORD);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn(new UserMember(loginUser));

        //when, then
        mockMvc.perform(delete(BASE_URI_KEYWORD_API + "/" + keyword)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("keywords-delete-my-keyword",
                        links(halLinks(),
                                linkWithRel("self")
                                        .description("link to self"),
                                linkWithRel("profile")
                                        .description("link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NULL)
                                        .description("It should be null"),
                                fieldWithPath("email")
                                        .type(JsonFieldType.NULL)
                                        .description("It should be null"),
                                fieldWithPath("keyword")
                                        .type(JsonFieldType.NULL)
                                        .description("It should be null"),
                                fieldWithPath("_links.self.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to self"),
                                fieldWithPath("_links.profile.href")
                                        .type(JsonFieldType.STRING)
                                        .description("link to profile")
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