package com.palmseung.members.atdd;

import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.dto.MemberResponseView;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.palmseung.members.MemberConstant.BASE_URI_USER_API;

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
                .post().uri(BASE_URI_USER_API)
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
}