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
    @DisplayName("정상 - Collection 사이즈")
    @Test
    void validateSize() {
        assertThatCode(() -> YoutubeVideos.of(createYoutubeVideos(5)))
                .doesNotThrowAnyException();
    }

    @DisplayName("비정상 - Collection 사이즈")
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 6})
    void validateSizeWhenAbnormal(int countOfVideo) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            YoutubeVideos.of(createYoutubeVideos(countOfVideo));
        }).withMessageContaining("video");
    }

    @DisplayName("정상 - viewCount 규칙")
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

    @DisplayName("비정상 - viewCount 규칙")
    @Test
    void validateViewCountRuleWhenAbnormal() {
        //given
        List<YoutubeVideo> youtubeVideos
                = Arrays.asList(TEST_YOUTUBE_VIDEO_2, TEST_YOUTUBE_VIDEO_1,
                TEST_YOUTUBE_VIDEO_3, TEST_YOUTUBE_VIDEO_4, TEST_YOUTUBE_VIDEO_5);

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            YoutubeVideos.of(youtubeVideos);
        });
    }

    private List<YoutubeVideo> createYoutubeVideos(int number) {
        return IntStream.range(0, number)
                .mapToObj(consume -> YoutubeVideo.builder().viewCount(1000 - number).build())
                .collect(Collectors.toList());
    }
}