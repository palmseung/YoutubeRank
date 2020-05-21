package com.palmseung.keywords.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.members.acceptancetest.MemberHttpTest;
import com.palmseung.members.dto.LoginResponseView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

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

        //when, then
        keywordHttpTest.addMyKeyword(keyword, accessToken);
    }

    @DisplayName("My keyword 조회")
    @Test
    public void retrieveMyKeyword() {
        //given
        String keyword = "queendom";
        keywordHttpTest.addMyKeyword(keyword, accessToken);

        //when
        MyKeywordResponseView response = keywordHttpTest.findMyKeyword(keyword, accessToken);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getKeyword()).isEqualTo(keyword);
    }

    @DisplayName("My Keyword 목록 조회")
    @Test
    public void retrieveAllMyKeywords() {
        //given
        keywordHttpTest.addMyKeyword("queendom", accessToken);
        keywordHttpTest.addMyKeyword("(g)idle", accessToken);
        keywordHttpTest.addMyKeyword("GOT7", accessToken);

        //when
        List<MyKeywordResponseView> responseViews = keywordHttpTest.findAllMyKeyword(accessToken);

        //then
        assertThat(responseViews).hasSize(3);
    }

    @DisplayName("My Keyword 삭제")
    @Test
    public void removeMyKeyword() {
        //given
        String keyword = "queendom";
        keywordHttpTest.addMyKeyword(keyword, accessToken);

        //when, then
        webTestClient.delete().uri(BASE_URI_KEYWORD_API + "/" + keyword)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .exchange()
                .expectStatus().isOk();
    }
}