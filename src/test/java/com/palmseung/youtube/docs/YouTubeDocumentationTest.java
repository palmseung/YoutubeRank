package com.palmseung.youtube.docs;

import com.google.api.client.json.Json;
import com.palmseung.BaseDocumentationTest;
import com.palmseung.youtube.service.YouTubeService;
import com.palmseung.youtube.support.YoutubeConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.palmseung.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.youtube.support.YoutubeConstant.TEST_YOUTUBE_VIDEOS;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class YouTubeDocumentationTest extends BaseDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    YouTubeService youTubeService;

    @DisplayName("[문서화] Youtube 키워드 검색 결과 가져 오기")
    @Test
    void search() throws Exception {
        //given
        String searchKeyword = "Queendom";
        given(youTubeService.search(searchKeyword)).willReturn(TEST_YOUTUBE_VIDEOS);

        mockMvc.perform(get(BASE_URI_YOUTUBE_API)
                .queryParam("search", searchKeyword)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("youtube-search",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        requestParameters(
                                parameterWithName("search")
                                        .description("A keyword to search on YouTube that a user typed")
                        ),
                        responseFields(
                                fieldWithPath("[].videoId")
                                        .type(JsonFieldType.STRING)
                                        .description("An intrinsic id of video issued by YouTube"),
                                fieldWithPath("[].title")
                                        .type(JsonFieldType.STRING)
                                        .description("A title of the YouTube video written by the uploader"),
                                fieldWithPath("[].url")
                                        .type(JsonFieldType.STRING)
                                        .description("The URL to play the video on YouTube"),
                                fieldWithPath("[].description")
                                        .type(JsonFieldType.STRING)
                                        .description("The description for the video written by the uploader"),
                                fieldWithPath("[].thumbnailUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("The URL to access the thumbnail image of the video in high quality"),
                                fieldWithPath("[].viewCount")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The view counts of the video at the time of request")
                        )
                ));
    }
}
