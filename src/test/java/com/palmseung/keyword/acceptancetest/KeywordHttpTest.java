package com.palmseung.keyword.acceptancetest;

import com.palmseung.keyword.dto.KeywordResponseView;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;

public class KeywordHttpTest {
    private WebTestClient webTestClient;

    public KeywordHttpTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public List<KeywordResponseView> addMyKeyword(String keyword, String accessToken){
        return webTestClient.post().uri(BASE_URI_KEYWORD_API)
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(keyword), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(KeywordResponseView.class)
                .returnResult().getResponseBody();
    }
}
