package com.palmseung.keyword.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.keyword.dto.MyKeywordResponseView;
import com.palmseung.member.acceptancetest.MemberHttpTest;
import com.palmseung.member.dto.LoginResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static com.palmseung.keyword.KeywordConstant.BASE_URI_KEYWORD_API;
import static com.palmseung.member.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class KeywordAcceptanceTest extends AbstractAcceptanceTest {
    private MemberHttpTest memberHttpTest;
    private KeywordHttpTest keywordHttpTest;
    private String accessToken;

    @BeforeEach
    void setUp() {
        memberHttpTest = new MemberHttpTest(webTestClient);
        keywordHttpTest = new KeywordHttpTest(webTestClient);
        memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD);
        LoginResponseView responseBody = memberHttpTest.login(TEST_EMAIL, TEST_PASSWORD).getResponseBody();
        accessToken = responseBody.getAccessToken();
    }

    @DisplayName("My Keyword 추가")
    @Test
    void addKeyword() {
        //given
        String keyword = "queendom";

        //when
        MyKeywordResponseView responseView = keywordHttpTest.addMyKeyword(keyword, accessToken);

        //then
        assertThat(responseView.getId()).isNotNull();
        assertThat(responseView.getKeyword()).isEqualTo("queendom");
    }

    @DisplayName("My Keyword 목록 조회")
    @Test
    public void retrieveAllMyKeywords() {
        //given
        keywordHttpTest.addMyKeyword("queendom", accessToken);
        keywordHttpTest.addMyKeyword("(g)idle", accessToken);

        //when
        List<MyKeywordResponseView> responseViews = webTestClient.get().uri(BASE_URI_KEYWORD_API)
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MyKeywordResponseView.class)
                .returnResult().getResponseBody();

        //then
        assertThat(responseViews).hasSize(2);
    }

    @DisplayName("My Keyword 삭제")
    @Test
    public void removeMyKeyword() {
        //given
        MyKeywordResponseView responseView = keywordHttpTest.addMyKeyword("queendom", accessToken);

        //when
        webTestClient.delete().uri(BASE_URI_KEYWORD_API + "/" + responseView.getId())
                .header("Authorization", accessToken)
                .exchange()
                .expectStatus().isOk();

        //then
        webTestClient.delete().uri(BASE_URI_KEYWORD_API + "/" + responseView.getId())
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}