package com.palmseung.youtube.service;

import com.palmseung.youtube.domain.YouTubeVideos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.palmseung.youtube.support.YoutubeConstant.YOUTUBE_NUMBER_OF_RESULT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class YouTubeServiceTest {
    @Autowired
    YouTubeService youTubeService;

    @DisplayName("Youtube Data Api 검색 결과 (정상)")
    @Test
    void search() throws IOException {
        //given
        String searchKeyword = "GOT7";

        //when
        YouTubeVideos results = youTubeService.search(searchKeyword);

        //then
        assertThat(results).isNotNull();
        assertThat(results.getYouTubeVideos().size()).isEqualTo(YOUTUBE_NUMBER_OF_RESULT);
    }
}