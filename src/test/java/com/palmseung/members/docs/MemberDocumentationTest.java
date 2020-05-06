package com.palmseung.members.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.dto.LoginRequestView;
import com.palmseung.members.dto.UpdateMemberRequestView;
import com.palmseung.members.service.MemberService;
import com.palmseung.members.support.JwtTokenProvider;
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

import static com.palmseung.members.MemberConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberDocumentationTest extends BaseDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

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


    @DisplayName("[문서화] 회원 조회")
    @Test
    public void retrieveMyInfo() throws Exception {
        //given
        Member member = createMember(TEST_EMAIL);
        String accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
        given(memberService.findById(member.getId())).willReturn(member);

        //when, then
        mockMvc.perform(get(BASE_URI_MY_INFO_API + "/" + member.getId())
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members-myInfo",
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
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .description("The email address to retrieve member information"),
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("The member's name to retrieve member information"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("The member's bcrypt encoded password")
                        )
                ));
    }

    @DisplayName("[문서화] 회원 탈퇴")
    @Test
    public void unsubscribe() throws Exception {
        //given
        Member member = createMember(TEST_EMAIL);
        String accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
        given(memberService.findById(member.getId())).willReturn(member);

        //when, then
        mockMvc.perform(delete(BASE_URI_MEMBER_API + "/" + member.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members-unsubscribe",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        )
                ));
    }

    @DisplayName("[문서화] 회원 정보 수정")
    @Test
    public void updateMyInfo() throws Exception {
        //given
        Member member = createMember(TEST_EMAIL);
        String accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
        String newName = "newName";
        String newPassword = "newPassword";
        UpdateMemberRequestView updateRequest = createUpdateRequest(newName, newPassword);
        Member updatedMember = updateMember(member, newName, newPassword);
        given(memberService.updateInfo(any(), any(), any())).willReturn(updatedMember);

        //when, then
        mockMvc.perform(put(BASE_URI_MY_INFO_API + "/" + member.getId())
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members-update-myInfo",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON),
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("The client should have valid access token produced on the server side")
                        ),
                        requestFields(
                                fieldWithPath("newName")
                                        .type(JsonFieldType.STRING)
                                        .description("The member's new name to update"),
                                fieldWithPath("newPassword")
                                        .type(JsonFieldType.STRING)
                                        .description("The member's new password to update")
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
                                        .description("The updated name of a valid member in the server"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("The updated password of a valid member in the server and it is bcrypt encoded")
                        )
                ));
    }

    private Member updateMember(Member member, String newName, String newPassword) {
        return Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(newName)
                .password(passwordEncoder.encode(newPassword))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }

    private UpdateMemberRequestView createUpdateRequest(String newName, String newPassword) {
        return UpdateMemberRequestView.builder()
                .newName(newName)
                .newPassword(newPassword)
                .build();
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
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }
}