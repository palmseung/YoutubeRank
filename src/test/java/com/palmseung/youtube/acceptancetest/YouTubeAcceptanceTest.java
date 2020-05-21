package com.palmseung.youtube.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.youtube.service.YouTubeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.TEST_YOUTUBE_VIDEOS;
import static org.mockito.BDDMockito.given;

public class YouTubeAcceptanceTest extends BaseAcceptanceTest {
    @MockBean
    private YouTubeService youTubeService;

    @DisplayName("Youtube 키워드 검색 결과 가져 오기")
    @Test
    void searchVideos() throws IOException {
        //given
        String keyword = "Queendom";
        given(youTubeService.search(keyword)).willReturn(TEST_YOUTUBE_VIDEOS);

        //when
        webTestClient.get().uri(BASE_URI_YOUTUBE_API + "?keyword=" + keyword)
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}