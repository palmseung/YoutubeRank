package com.palmseung.keywords.acceptancetest;

import com.palmseung.keywords.dto.MyKeywordRequestView;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.palmseung.keywords.KeywordConstant.BASE_URI_KEYWORD_API;

public class KeywordHttpTest {
    private WebTestClient webTestClient;

    public KeywordHttpTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public void addMyKeyword(String keyword, String accessToken) {
        webTestClient.post().uri(BASE_URI_KEYWORD_API)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest(keyword))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    public MyKeywordResponseView findMyKeyword(String keyword, String accessToken) {
        return webTestClient.get().uri(BASE_URI_KEYWORD_API + "/" + keyword)
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

    private MyKeywordRequestView createRequest(String keyword) {
        return MyKeywordRequestView.builder()
                .keyword(keyword)
                .build();
    }
}