package com.palmseung.youtube.acceptancetest;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.youtube.domain.YoutubeVideo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class YoutubeAcceptanceTest extends BaseAcceptanceTest {
    @DisplayName("Youtube 키워드 검색 결과 가져오기")
    @Test
    void searchVideos() {
        //given
        String keyword = "Queendom";

        //when
        List<YoutubeVideo> youtubeVideos = webTestClient.get().uri(BASE_URI_YOUTUBE_API + "?search=" + keyword)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(YoutubeVideo.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(youtubeVideos.size()).isEqualTo(5);

        Long viewCount = 0l;
        for (YoutubeVideo youtubeVideo : youtubeVideos) {
            assertThat(youtubeVideo.getUrl()).contains(YOUTUBE_URL);
            assertThat(youtubeVideo.getVideoId()).isNotEmpty();
            assertThat(youtubeVideo.getViewCount()).isLessThanOrEqualTo(viewCount);
            assertThat(youtubeVideo.getThumbnail()).isNotEmpty();
            assertThat(youtubeVideo.getDescription()).isNotNull();
            viewCount = youtubeVideo.getViewCount();
        }
    }
}