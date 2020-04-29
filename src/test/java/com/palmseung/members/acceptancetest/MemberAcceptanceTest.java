package com.palmseung.members.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.members.dto.LoginResponseView;
import com.palmseung.members.dto.MemberResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AbstractAcceptanceTest {
    private MemberHttpTest memberHttpTest;

    @BeforeEach
    public void setUp() {
        this.memberHttpTest = new MemberHttpTest(webTestClient);
    }

    @DisplayName("회원 가입")
    @Test
    public void signUp() {
        //when
        MemberResponseView response = createMember();

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
                .header("Authorization", responseBody.getAccessToken())
                .exchange()
                .expectStatus().isNoContent();
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

    @DisplayName("회원 정보 조회")
    @Test
    public void retrieveMyInfo() {
        //given
        Long id = createMember().getId();
        LoginResponseView responseView = doLogin();

        //when, then
        webTestClient.get().uri(BASE_URI_MY_INFO_API + "/" + id)
                .header("Authorization", responseView.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").exists()
                .jsonPath("$.password").exists()
                .jsonPath("$.name").exists();
    }

    private MemberResponseView createMember() {
        return memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD).getResponseBody();
    }

    private LoginResponseView doLogin() {
        return memberHttpTest.login(TEST_EMAIL, TEST_PASSWORD).getResponseBody();
    }
}