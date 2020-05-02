package com.palmseung.keyword.service;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.domain.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {
    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @DisplayName("키워드 - 생성")
    @Test
    void createKeyword() {
        //given
        Keyword keyword = Keyword.builder()
                .keyword("queendom")
                .build();

        //when
        Keyword createdKeyword = keywordService.create(keyword);

        //then
        verify(keywordRepository, times(1)).save(any(Keyword.class));
    }
}