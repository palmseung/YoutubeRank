package com.palmseung.modules.youtube.docs;

import com.palmseung.BaseDocumentationTest;
import com.palmseung.infra.jwt.JwtTokenProvider;
import com.palmseung.modules.members.service.MemberService;
import com.palmseung.modules.youtube.service.YouTubeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.palmseung.modules.members.MemberConstant.TEST_EMAIL;
import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static com.palmseung.modules.youtube.support.YoutubeConstant.BASE_URI_YOUTUBE_API;
import static com.palmseung.modules.youtube.support.YoutubeConstant.TEST_YOUTUBE_VIDEOS;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class YouTubeDocumentationTest extends BaseDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private YouTubeService youTubeService;

    @MockBean
    private MemberService memberService;

    @DisplayName("[문서화] Youtube 키워드 검색 결과 가져 오기")
    @Test
    void search() throws Exception {
        //given
        String searchKeyword = "Queendom";
        String accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
        given(youTubeService.search(searchKeyword)).willReturn(TEST_YOUTUBE_VIDEOS);
        given(memberService.loadUserByUsername(TEST_EMAIL)).willReturn((UserDetails) TEST_MEMBER);

        mockMvc.perform(get(BASE_URI_YOUTUBE_API)
                .queryParam("keyword", searchKeyword)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("youtube-search",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT)
                                        .description(MediaType.APPLICATION_JSON)
                        ),
                        requestParameters(
                                parameterWithName("keyword")
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