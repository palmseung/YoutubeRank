package com.palmseung.members.acceptancetest;

import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.dto.LoginRequestView;
import com.palmseung.members.dto.LoginResponseView;
import com.palmseung.members.dto.MemberResponseView;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.palmseung.members.MemberConstant.*;

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
}