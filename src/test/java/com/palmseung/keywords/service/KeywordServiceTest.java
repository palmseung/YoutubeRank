package com.palmseung.keywords.service;

import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.palmseung.keywords.KeywordConstant.TEST_KEYWORD;
import static com.palmseung.keywords.KeywordConstant.TEST_MY_KEYWORD;
import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {
    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private MyKeywordRepository myKeywordRepository;

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
}
