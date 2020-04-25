package com.palmseung.members.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.members.domain.MemberRole;
import com.palmseung.members.dto.LoginRequestView;
import com.palmseung.members.dto.MemberResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

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
        assertThat(response.getMemberRole()).isEqualTo(MemberRole.USER);
    }

    @DisplayName("회원 탈퇴")
    @Test
    public void unsubscribe() {
        //given
        Long id = createMember().getId();

        //when, then
        webTestClient.delete().uri(BASE_URI_USER_API + "/" + id)
                .exchange()
                .expectStatus().isNoContent();
    }

    @DisplayName("로그인")
    @Test
    public void login() {
        //given
        createMember();
        LoginRequestView requestView = createLoginRequest();

        //when, then
        webTestClient.post().uri(BASE_URI_LOGIN_API)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestView), LoginRequestView.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .jsonPath("$.tokenType").isNotEmpty();
    }

    private LoginRequestView createLoginRequest() {
        return LoginRequestView.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }

    private MemberResponseView createMember() {
        return memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD).getResponseBody();
    }
}