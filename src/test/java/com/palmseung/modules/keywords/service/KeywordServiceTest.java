package com.palmseung.modules.keywords.service;

import com.palmseung.BaseContainerTest;
import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.domain.KeywordRepository;
import com.palmseung.modules.keywords.domain.MyKeyword;
import com.palmseung.modules.keywords.domain.MyKeywordRepository;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.palmseung.modules.keywords.KeywordConstant.*;
import static com.palmseung.modules.members.MemberConstant.TEST_EMAIL;
import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest extends BaseContainerTest {
    @Autowired
    private KeywordService keywordService;

    @MockBean
    private KeywordRepository keywordRepository;

    @MockBean
    private MyKeywordRepository myKeywordRepository;

    @MockBean
    private MemberService memberService;

    @DisplayName("MyKeyword 등록")
    @Test
    void addMyKeyword() {
        //given
        given(memberService.findByEmail(anyString())).willReturn(TEST_MEMBER);
        given(keywordRepository.save(any(Keyword.class))).willReturn(TEST_KEYWORD);
        given(myKeywordRepository.save(any(MyKeyword.class))).willReturn(TEST_MY_KEYWORD);

        //when
        MyKeyword myKeyword = keywordService.addMyKeyword(TEST_MEMBER, TEST_KEYWORD.getKeyword());

        //then
        assertThat(myKeyword.getId()).isNotNull();
        assertThat(myKeyword.getStringKeyword()).isEqualTo(TEST_KEYWORD.getKeyword());
        assertThat(myKeyword.getMember().getEmail()).isEqualTo(TEST_EMAIL);
    }

    @DisplayName("MyKeyword 조회")
    @Test
    void findMyKeywordByKeywordIdAndMemberId() {
        //given
        String keyword = TEST_KEYWORD.getKeyword();
        String email = TEST_MEMBER.getEmail();
        given(keywordRepository.findByKeyword(keyword)).willReturn(Optional.of(TEST_KEYWORD));
        given(myKeywordRepository.findByKeywordIdAndMemberId(TEST_KEYWORD.getId(), TEST_MEMBER.getId()))
                .willReturn(Optional.of(TEST_MY_KEYWORD));

        //when
        MyKeyword myKeyword = keywordService.findMyKeyword(TEST_MEMBER, keyword);

        //then
        assertThat(myKeyword.getStringKeyword()).isEqualTo(keyword);
        assertThat(myKeyword.getMember().getEmail()).isEqualTo(email);
    }

    @DisplayName("MyKeyword 목록 조회")
    @Test
    void findAllMyKeywords() {
        //given
        given(myKeywordRepository.findAllByMemberId(TEST_MEMBER.getId()))
                .willReturn(Arrays.asList(TEST_MY_KEYWORD, TEST_MY_KEYWORD_2, TEST_MY_KEYWORD_3));

        //when
        List<MyKeyword> allMyKeyword = keywordService.findAllMyKeyword(TEST_MEMBER);

        //then
        assertThat(allMyKeyword.size()).isEqualTo(3);
    }

    @DisplayName("MyKeyword 삭제")
    @Test
    public void deleteMyKeyword() {
        //given
        given(keywordRepository.findByKeyword(TEST_KEYWORD.getKeyword()))
                .willReturn(Optional.of(TEST_KEYWORD));
        given(myKeywordRepository.findByKeywordIdAndMemberId(TEST_MEMBER.getId(), TEST_KEYWORD.getId()))
                .willReturn(Optional.of(TEST_MY_KEYWORD));

        //when
        keywordService.deleteMyKeyword(TEST_MEMBER, TEST_KEYWORD.getKeyword());

        //then
        verify(myKeywordRepository, times(1)).deleteById(TEST_MY_KEYWORD.getId());
    }

    @DisplayName("MyKeyword 목록 조회")
    @Test
    void getAllMyKeywords() {
        //given
        given(myKeywordRepository.findAllByMemberId(anyLong())).willReturn(Arrays.asList(TEST_MY_KEYWORD));

        //when
        List<KeywordResponseView> keywords = keywordService.getKeywords(TEST_MEMBER);

        //then
        assertThat(keywords.size()).isEqualTo(1);
    }
}