package com.palmseung.member.acceptancetest;

import com.palmseung.member.domain.Member;
import com.palmseung.member.dto.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.palmseung.member.MemberConstant.*;

public class MemberHttpTest {
    private WebTestClient webTestClient;

    public MemberHttpTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public EntityExchangeResult<MemberResponseView> createMember(String email, String name, String password) {
        CreateMemberRequestView requestView = CreateMemberRequestView.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        return webTestClient
                .post().uri(BASE_URI_MEMBER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestView), CreateMemberRequestView.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectHeader().exists("Location")
                .expectBody(MemberResponseView.class)
                .returnResult();
    }

    public EntityExchangeResult<LoginResponseView> login(String email, String password) {
        LoginRequestView requestView = LoginRequestView.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        return webTestClient.post().uri(BASE_URI_LOGIN_API)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestView), LoginRequestView.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LoginResponseView.class)
                .returnResult();
    }

    public EntityExchangeResult<MemberResponseView> retrieveMyInfo(Long id, LoginResponseView responseView) {
        return webTestClient.get().uri(BASE_URI_MY_INFO_API + "/" + id)
                .header("Authorization", responseView.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberResponseView.class)
                .returnResult();
    }

    public EntityExchangeResult<MemberResponseView> updateMyInfo(Member loginUser,
                                                                 LoginResponseView responseView,
                                                                 String newName,
                                                                 String newPassword) {
        UpdateMemberRequestView requestView = UpdateMemberRequestView.builder()
                .id(loginUser.getId())
                .email(loginUser.getEmail())
                .name(newName)
                .password(newPassword)
                .build();

        return webTestClient.put().uri(BASE_URI_MY_INFO_API + "/" + loginUser.getId())
                .header("Authorization", responseView.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestView), UpdateMemberRequestView.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberResponseView.class)
                .returnResult();
    }
}