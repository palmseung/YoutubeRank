package com.palmseung.keyword.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.keyword.KeywordConstant;
import com.palmseung.keyword.dto.KeywordResponseView;
import com.palmseung.member.acceptancetest.MemberHttpTest;
import com.palmseung.member.dto.LoginResponseView;
import org.junit.jupiter.api.*;
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
        List<KeywordResponseView> responseViews = keywordHttpTest.addMyKeyword(keyword, accessToken);

        //then
        assertThat(responseViews.get(0).getId()).isEqualTo(1l);
        assertThat(responseViews.get(0).getKeyword()).isEqualTo("queendom");
    }

    @DisplayName("My Keywords 목록 조회")
    @Test
    void retrieveAllMyKeywords(){
        //given
        String keyword1 = "queendom";
        String keyword2 = "(g)idle";
        keywordHttpTest.addMyKeyword(keyword1, accessToken);
        keywordHttpTest.addMyKeyword(keyword2, accessToken);

        //when
        List<KeywordResponseView> responseBody = webTestClient.get().uri(BASE_URI_KEYWORD_API)
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(KeywordResponseView.class)
                .returnResult().getResponseBody();

        //then
        assertThat(responseBody).hasSize(2);
    }
}