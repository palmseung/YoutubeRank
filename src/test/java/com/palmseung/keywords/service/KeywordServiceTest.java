package com.palmseung.keywords.service;

import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.palmseung.keywords.KeywordConstant.*;
import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {
    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private MyKeywordRepository myKeywordRepository;

    @DisplayName("MyKeyword 조회")
    @Test
    void findMyKeywordByKeywordIdAndMemberId() {
        //given
        String keyword = TEST_KEYWORD.getKeyword();
        String email = TEST_MEMBER.getEmail();
        given(keywordRepository.findByKeyword(keyword))
                .willReturn(Optional.of(TEST_KEYWORD));
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
}
