package com.palmseung.keywords.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.members.service.MemberService;
import com.palmseung.support.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static com.palmseung.keywords.KeywordConstant.BASE_URI_KEYWORD_API;
import static com.palmseung.keywords.KeywordConstant.TEST_MY_KEYWORD;
import static com.palmseung.members.MemberConstant.TEST_EMAIL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KeywordDocumentationTest extends BaseDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberService memberService;

    @DisplayName("[문서화] My Keyword 추가")
    @Test
    public void addMyKeyword() throws Exception {
        //given
        String keyword = "queendom";
        MyKeywordRequestView requestView = MyKeywordRequestView.builder()
                .keyword(keyword)
                .build();
        given(memberService.addKeyword(any(), any())).willReturn(TEST_MY_KEYWORD);

        //when, then
        mockMvc.perform(post(BASE_URI_KEYWORD_API)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, createToken(TEST_EMAIL))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestView)))
                .andExpect(status().isOk())
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
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The intrinsic myKeyword id"),
                                fieldWithPath("keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword added to member's my-keyword")
                        )
                ));
    }

    @DisplayName("[문서화] My Keyword 조회")
    @Test
    public void retrieveMyKeyword() throws Exception {
        //given
        String keyword = "queendom";
        given(memberService.findMyKeywordByMyKeywordId(any(), anyLong())).willReturn(TEST_MY_KEYWORD);

        //when
        mockMvc.perform(get(BASE_URI_KEYWORD_API + "/" + TEST_MY_KEYWORD.getId())
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
                                fieldWithPath("keyword")
                                        .type(JsonFieldType.STRING)
                                        .description("The keyword added to member's my-keyword to retrieve")
                        )
                ));
    }

    private String createToken(String email) {
        return jwtTokenProvider.createToken(TEST_EMAIL);
    }
}