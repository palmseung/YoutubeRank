package com.palmseung.youtube.support;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.palmseung.youtube.domain.YoutubeVideo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YoutubeConstant {
    public static final String BASE_URI_YOUTUBE_API = "/api/youtube";

    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    public static final long YOUTUBE_MAX_RESULTS = 5;

    public static final YoutubeVideo TEST_YOUTUBE_VIDEO_1
            = YoutubeVideo.builder()
            .title("[ENG sub] [4회] ♬ Fire - (여자)아이들 @2차 경연   커버곡 대결 컴백전쟁 : 퀸덤 4화")
            .url("https://www.youtube.com/watch?v=Og_X5gTXTF0")
            .videoId("Og_X5gTXTF0")
            .viewCount(21_804_641)
            .description("[4회] ♬ Fire - (여자)아이들 @2차 경연 : 커버곡 대결 K-POP 여왕의 자리를 두고 펼쳐지는 걸그룹 컴백 전쟁! Mnet '퀸덤(Queendom)' ----------------------------...")
            .thumbnail("https://i.ytimg.com/vi/Og_X5gTXTF0/hqdefault.jpg")
            .build();

    public static final YoutubeVideo TEST_YOUTUBE_VIDEO_2
            = YoutubeVideo.builder()
            .title("[ENG sub] [3회] ♬ 너나 해(Egotistic) - AOA @2차 경연ㅣ커버곡 대결 컴백전쟁 : 퀸덤 3화")
            .url("https://www.youtube.com/watch?v=JXC7rtAVOzM")
            .videoId("JXC7rtAVOzM")
            .viewCount(19_234_333)
            .description("[3회] ♬ 너나 해(Egotistic) - AOA @2차 경연ㅣ커버곡 대결 K-POP 여왕의 자리를 두고 펼쳐지는 걸그룹 컴백 전쟁! Mnet '퀸덤(Queendom)' -------------------------...")
            .thumbnail("https://i.ytimg.com/vi/JXC7rtAVOzM/hqdefault.jpg")
            .build();

    public static final YoutubeVideo TEST_YOUTUBE_VIDEO_3
            = YoutubeVideo.builder()
            .title("[ENG sub] [최종회] ♬ LION - (여자)아이들 @ FINAL 경연 컴백전쟁 : 퀸덤 10화")
            .url("https://www.youtube.com/watch?v=2KtFPjSp3og")
            .videoId("2KtFPjSp3og")
            .viewCount(17_694_213)
            .description("[최종회] ♬ LION - (여자)아이들 @ FINAL 경연 K-POP 여왕의 자리를 두고 펼쳐지는 걸그룹 컴백 전쟁! Mnet '퀸덤(Queendom)' --------------------------------...")
            .thumbnail("https://i.ytimg.com/vi/JXC7rtAVOzM/hqdefault.jpg")
            .build();

    public static final YoutubeVideo TEST_YOUTUBE_VIDEO_4
            = YoutubeVideo.builder()
            .title("[ENG sub] [2회] ♬ LATATA - (여자)아이들 @1차 경연ㅣ히트곡 대결 컴백전쟁 : 퀸덤 2화")
            .url("https://www.youtube.com/watch?v=Knz0j_G7lKg")
            .videoId("Knz0j_G7lKg")
            .viewCount(15_764_132)
            .description("[2회] ♬ LATATA - (여자)아이들 @1차 경연ㅣ히트곡 대결 K-POP 여왕의 자리를 두고 펼쳐지는 걸그룹 컴백 전쟁! Mnet '퀸덤(Queendom)' --------------------------...")
            .thumbnail("https://i.ytimg.com/vi/Knz0j_G7lKg/hqdefault.jpg")
            .build();

    public static final YoutubeVideo TEST_YOUTUBE_VIDEO_5
            = YoutubeVideo.builder()
            .title("[ENG sub] [3회] ♬ 한(一) feat.치타 - 박봄 @2차 경연ㅣ커버곡 대결 컴백전쟁 : 퀸덤 3화")
            .url("https://www.youtube.com/watch?v=QxOr7l7utUo")
            .videoId("QxOr7l7utUo")
            .viewCount(12_764_130)
            .description("[3회] ♬ 한(一) feat.치타 - 박봄 @2차 경연ㅣ커버곡 대결 K-POP 여왕의 자리를 두고 펼쳐지는 걸그룹 컴백 전쟁! Mnet '퀸덤(Queendom)' -----------------------...")
            .thumbnail("https://i.ytimg.com/vi/QxOr7l7utUo/hqdefault.jpg")
            .build();
}
