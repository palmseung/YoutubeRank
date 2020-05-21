package com.palmseung.members.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends BaseAcceptanceTest {
    private MemberHttpTest memberHttpTest;

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        this.memberHttpTest = new MemberHttpTest(webTestClient);
    }

    @AfterEach
    public void cleanDatabase() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원 가입")
    @Test
    public void signUp() {
        //when
        CreateMemberResponseView response = createMember();

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(response.getPassword()).contains("bcrypt");
    }

    @DisplayName("회원 탈퇴")
    @Test
    public void unsubscribe() {
        //given
        Long id = createMember().getId();
        LoginResponseView responseBody = doLogin();

        //when, then
        webTestClient.delete().uri(BASE_URI_MEMBER_API + "/" + id)
                .header(HttpHeaders.AUTHORIZATION, responseBody.getAccessToken())
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("회원 정보 조회")
    @Test
    public void retrieveMyInfo() {
        //given
        Long id = createMember().getId();
        LoginResponseView responseView = doLogin();

        //when, then
        MyInfoResponseView response
                = memberHttpTest.retrieveMyInfo(id, responseView).getResponseBody();

        //then
        assertThat(response.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(response.getName()).isEqualTo(TEST_NAME);
        assertThat(response.getPassword()).contains("bcrypt");
    }

    @DisplayName("회원 정보 수정")
    @WithMockUser(roles = "USER")
    @Test
    void updateMyInfo() {
        //given
        Long id = createMember().getId();
        LoginResponseView responseView = doLogin();
        String newPassword = "newPassword";

        //when
        UpdateMemberResponseView responseBody
                = memberHttpTest.updateMyInfo(TEST_MEMBER, responseView, newPassword)
                .getResponseBody();

        //then
        assertThat(responseBody.getId()).isEqualTo(id);
        assertThat(responseBody.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(responseBody.getPassword()).isNotEqualTo(passwordEncoder.encode(TEST_PASSWORD));

    }

    @DisplayName("로그인")
    @Test
    public void login() {
        //given
        createMember();

        //when
        LoginResponseView response = doLogin();

        //then
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getTokenType()).isEqualTo("Bearer ");
    }

//    @DisplayName("로그아웃")
//    @Test
//    public void logout(){
//        //given
//        createMember();
//        doLogin();
//
//        //when
//        webTestClient.get()
//    }

    private CreateMemberResponseView createMember() {
        return memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD).getResponseBody();
    }

    private LoginResponseView doLogin() {
        return memberHttpTest.login(TEST_EMAIL, TEST_PASSWORD).getResponseBody();
    }
}