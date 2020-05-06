package com.palmseung.youtube;

import com.palmseung.youtube.domain.YoutubeVideo;
import com.palmseung.youtube.domain.YoutubeVideos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.palmseung.youtube.support.YoutubeConstant.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class YoutubeVideosTest {
    @DisplayName("정상 - Collection 사이즈가 5일 때 객체 생성")
    @Test
    void validateSize() {
        assertThatCode(() -> YoutubeVideos.of(createYoutubeVideos(5)))
                .doesNotThrowAnyException();
    }

    @DisplayName("비정상 - Collection 사이즈가 5가 아닐 때")
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 6})
    void validateSizeWhenAbnormal(int countOfVideo) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            YoutubeVideos.of(createYoutubeVideos(countOfVideo));
        }).withMessageContaining("video");
    }

    @DisplayName("Collection의 index가 커질수록 viewCount는 작아진다. (정상)")
    @Test
    void validateViewCountRule() {
        //given
        List<YoutubeVideo> youtubeVideos
                = Arrays.asList(TEST_YOUTUBE_VIDEO_1, TEST_YOUTUBE_VIDEO_2,
                TEST_YOUTUBE_VIDEO_3, TEST_YOUTUBE_VIDEO_4, TEST_YOUTUBE_VIDEO_5);

        //when, then
        assertThatCode(() -> {
            YoutubeVideos.of(youtubeVideos);
        }).doesNotThrowAnyException();
    }

    private List<YoutubeVideo> createYoutubeVideos(int number) {
        return IntStream.range(0, number)
                .mapToObj(consume -> YoutubeVideo.builder().viewCount(1000 - number).build())
                .collect(Collectors.toList());
    }
}