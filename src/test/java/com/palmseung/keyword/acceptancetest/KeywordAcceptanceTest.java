package com.palmseung.keyword.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.keyword.dto.KeywordResponseView;
import com.palmseung.member.acceptancetest.MemberHttpTest;
import com.palmseung.member.dto.LoginResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;
import static com.palmseung.member.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class KeywordAcceptanceTest extends AbstractAcceptanceTest {
    private MemberHttpTest memberHttpTest;
    private String accessToken;

    @BeforeEach
    void setUp() {
        memberHttpTest = new MemberHttpTest(webTestClient);
        memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD);
        LoginResponseView responseBody = memberHttpTest.login(TEST_EMAIL, TEST_PASSWORD).getResponseBody();
        accessToken = responseBody.getAccessToken();
    }

    @DisplayName("My Keyword 추가")
    @Test
    void addKeyword() {
        //given
        String keyword = "queendom";

        List<KeywordResponseView> responseViews = webTestClient.post().uri(BASE_URI_KEYWORD_API)
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(keyword), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(KeywordResponseView.class)
                .returnResult().getResponseBody();

        assertThat(responseViews.get(0).getId()).isEqualTo(1l);
        assertThat(responseViews.get(0).getKeyword()).isEqualTo("queendom");
    }
}