package com.palmseung.keyword.service;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.domain.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public Keyword create(Keyword keyword) {
        return keywordRepository.findByKeyword(keyword.getKeyword())
                .orElseGet(() -> keywordRepository.save(keyword));
    }
}
