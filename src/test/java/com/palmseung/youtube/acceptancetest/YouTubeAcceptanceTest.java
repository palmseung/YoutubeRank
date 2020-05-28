package com.palmseung.youtube.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.members.jwt.JwtTokenProvider;
import com.palmseung.members.service.MemberService;
import com.palmseung.youtube.domain.YouTubeVideo;
import com.palmseung.youtube.service.YouTubeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

import static com.palmseung.members.MemberConstant.TEST_EMAIL;
import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static com.palmseung.youtube.support.YoutubeConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class YouTubeAcceptanceTest extends BaseAcceptanceTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private YouTubeService youTubeService;

    @MockBean
    private MemberService memberService;

    @DisplayName("Youtube 키워드 검색 결과 가져 오기")
    @Test
    void searchVideos() throws IOException {
        //given
        String keyword = "Queendom";
        String accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
        given(youTubeService.search(keyword)).willReturn(TEST_YOUTUBE_VIDEOS);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn((UserDetails) TEST_MEMBER);

        //when
        List<YouTubeVideo> youTubeVideos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URI_YOUTUBE_API)
                        .queryParam("keyword", keyword)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(YouTubeVideo.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(youTubeVideos.size()).isEqualTo(5);
        for (YouTubeVideo youtubeVideo : youTubeVideos) {
            assertThat(youtubeVideo.getUrl()).contains(YOUTUBE_URL);
            assertThat(youtubeVideo.getVideoId()).isNotEmpty();
            assertThat(youtubeVideo.getViewCount()).isNotNegative();
            assertThat(youtubeVideo.getThumbnailUrl()).isNotEmpty();
            assertThat(youtubeVideo.getDescription()).isNotNull();
        }
    }
}