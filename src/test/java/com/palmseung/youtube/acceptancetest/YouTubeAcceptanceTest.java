package com.palmseung.youtube.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.youtube.domain.YouTubeVideo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class YouTubeAcceptanceTest extends BaseAcceptanceTest {
    @DisplayName("Youtube 키워드 검색 결과 가져 오기")
    @Test
    void searchVideos() {
        //given
        String keyword = "Queendom";

        //when
        List<YouTubeVideo> youTubeVideos = webTestClient.get().uri(BASE_URI_YOUTUBE_API + "?search=" + keyword)
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