package com.palmseung.youtube;

import com.palmseung.youtube.domain.YoutubeVideo;
import com.palmseung.youtube.domain.YoutubeVideos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private List<YoutubeVideo> createYoutubeVideos(int number) {
        return IntStream.range(0, number)
                .mapToObj(consume -> new YoutubeVideo())
                .collect(Collectors.toList());
    }
}