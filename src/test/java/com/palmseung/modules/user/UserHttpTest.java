package com.palmseung.modules.user;

import com.palmseung.modules.users.dto.CreateUserRequestView;
import com.palmseung.modules.users.dto.UserResponseView;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.palmseung.modules.users.dto.UserConstant.BASE_URI_USER;

public class UserHttpTest {
    private WebTestClient webTestClient;

    public UserHttpTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public EntityExchangeResult<UserResponseView> createUser(String email, String name, String password) {
        CreateUserRequestView requestView = CreateUserRequestView.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        return webTestClient.post().uri(BASE_URI_USER)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(requestView), CreateUserRequestView.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectHeader().exists("Location")
                .expectBody(UserResponseView.class)
                .returnResult();
    }
}