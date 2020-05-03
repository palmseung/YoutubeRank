package com.palmseung.members.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.members.dto.CreateMemberRequestView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static com.palmseung.members.MemberConstant.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberDocumentationTest extends BaseDocumentationTest {
    @Autowired
    public MockMvc mockMvc;

    @DisplayName("[문서화] 회원 가입")
    @Test
    void signUp() throws Exception {
        CreateMemberRequestView requestView = CreateMemberRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();

        mockMvc.perform(post(BASE_URI_MEMBER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestView)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("members-create",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("회원 가입하려는 사용자의 이메일 주소"),
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("회원 가입하려는 사용자의 이름"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("회원 가입하려는 사용자의 비밀번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("가입된 회원에게 부여되는 고유 ID(Long type)"),
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("가입된 회원의 이메일 주소"),
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("가입된 회원의 이름"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("가입된 회원의 암호화 된 비밀번호")
                        )));
    }
}