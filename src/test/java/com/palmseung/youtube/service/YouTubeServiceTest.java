package com.palmseung.youtube.service;

import com.palmseung.youtube.domain.YoutubeVideos;
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

    @Test
    void search() throws IOException {
        //given
        String searchKeyword = "GOT7";

        //when
        YoutubeVideos results = youTubeService.search(searchKeyword);

        //then
        assertThat(results).isNotNull();
        assertThat(results.getYoutubeVideos().size()).isEqualTo(YOUTUBE_NUMBER_OF_RESULT);
    }
}