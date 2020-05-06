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
    @DisplayName("Collection 사이즈가 5일 때 객체 생성 (정상)")
    @Test
    void validateSize() {
        assertThatCode(() -> YoutubeVideos.of(createYoutubeVideos(5)))
                .doesNotThrowAnyException();
    }

    @DisplayName("Collection")
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 6})
    void validateSizeWhenAbnormal(int countOfVideo){
        assertThatIllegalArgumentException().isThrownBy(() ->{
            YoutubeVideos.of(createYoutubeVideos(countOfVideo));
        }).withMessageContaining("video");
    }

    private List<YoutubeVideo> createYoutubeVideos(int number) {
        return IntStream.range(0, number)
                .mapToObj(consume -> new YoutubeVideo())
                .collect(Collectors.toList());
    }
}