package com.palmseung.keyword.acceptancetest;

import com.palmseung.keyword.dto.MyKeywordResponseView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;

public class KeywordHttpTest {
    private WebTestClient webTestClient;

    public KeywordHttpTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public MyKeywordResponseView addMyKeyword(String keyword, String accessToken) {
        return webTestClient.post().uri(BASE_URI_KEYWORD_API)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(keyword), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyKeywordResponseView.class)
                .returnResult().getResponseBody();
    }

    public MyKeywordResponseView findMyKeyword(Long id, String accessToken) {
        return webTestClient.get().uri(BASE_URI_KEYWORD_API + "/" + id)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyKeywordResponseView.class)
                .returnResult()
                .getResponseBody();
    }

    public List<MyKeywordResponseView> findAllMyKeyword(String accessToken) {
        return webTestClient.get().uri(BASE_URI_KEYWORD_API)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MyKeywordResponseView.class)
                .returnResult().getResponseBody();
    }
}