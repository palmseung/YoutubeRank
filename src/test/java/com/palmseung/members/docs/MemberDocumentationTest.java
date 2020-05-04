package com.palmseung.members.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.dto.LoginRequestView;
import com.palmseung.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.palmseung.members.MemberConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberDocumentationTest extends BaseDocumentationTest {
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("[문서화] 회원 가입")
    @Test
    void signUp() throws Exception {
        //given
        given(memberService.create(any())).willReturn(createMember(TEST_EMAIL));
        CreateMemberRequestView createMemberRequest = createMemberRequest(TEST_EMAIL);

        //when, then
        mockMvc.perform(post(BASE_URI_MEMBER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberRequest)))
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
                                        .description("The email address to sign-up that should be unique"),
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("The user name to sign-up"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("The password to sign-up")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The intrinsic member number issued on the server side"),
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("The unique email of a valid member in the server"),
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("The name of a valid member in the server"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("The password of a valid member in the server and it is bcrypt encoded")
                        )));
    }

    @DisplayName("[문서화] 로그인")
    @Test
    public void login() throws Exception {
        //given
        given(memberService.login(TEST_EMAIL, TEST_PASSWORD)).willReturn(createMember(TEST_EMAIL));
        LoginRequestView loginRequest = createLoginRequest(TEST_EMAIL);

        //when, then
        mockMvc.perform(post(BASE_URI_LOGIN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("members-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("The email to login"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("The password to login"),
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.NULL)
                                        .description("It should be null and will be produced on the server side if the member is a valid member"),
                                fieldWithPath("tokenType")
                                        .type(JsonFieldType.NULL)
                                        .description("It should be null and will be produced on the server side")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.LOCATION)
                                        .description("The resource is crated")
                        ),
                        responseFields(
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.STRING)
                                        .description("The accessToken produced by the server when login is successful"),
                                fieldWithPath("tokenType")
                                        .type(JsonFieldType.STRING)
                                        .description("It should be Bearer type in this application")
                        )
                ));
    }

    private LoginRequestView createLoginRequest(String email) {
        return LoginRequestView.builder()
                .email(email)
                .password(TEST_PASSWORD)
                .build();
    }

    private CreateMemberRequestView createMemberRequest(String email) {
        return CreateMemberRequestView.builder()
                .email(email)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }

    private Member createMember(String email) {
        return Member.builder()
                .id(TEST_ID)
                .email(email)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }
}