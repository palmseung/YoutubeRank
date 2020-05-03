package com.palmseung.keywords.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.members.acceptancetest.MemberHttpTest;
import com.palmseung.members.dto.LoginResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static com.palmseung.keywords.KeywordConstant.BASE_URI_KEYWORD_API;
import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class KeywordAcceptanceTest extends BaseAcceptanceTest {
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

    @DisplayName("My keyword 조회")
    @Test
    public void retrieveMyKeyword() {
        //given
        MyKeywordResponseView responseView = keywordHttpTest.addMyKeyword("queendom", accessToken);

        //when
        MyKeywordResponseView response = keywordHttpTest.findMyKeyword(responseView.getId(), accessToken);

        //then
        assertThat(response.getId()).isEqualTo(responseView.getId());
        assertThat(response.getKeyword()).isEqualTo(responseView.getKeyword());
    }

    @DisplayName("My Keyword 목록 조회")
    @Test
    public void retrieveAllMyKeywords() {
        //given
        keywordHttpTest.addMyKeyword("queendom", accessToken);
        keywordHttpTest.addMyKeyword("(g)idle", accessToken);

        //when
        List<MyKeywordResponseView> responseViews = keywordHttpTest.findAllMyKeyword(accessToken);

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
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .exchange()
                .expectStatus().isOk();

        //then
        webTestClient.delete().uri(BASE_URI_KEYWORD_API + "/" + responseView.getId())
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}