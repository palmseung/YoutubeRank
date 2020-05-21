package com.palmseung.keywords.service;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;

    public MyKeyword findMyKeywordByKeyword(Member loginUser, String keyword) {
        Keyword byKeyword
                = keywordRepository
                .findByKeyword(keyword)
                .orElseThrow(() -> new IllegalArgumentException("dd"));
        return myKeywordRepository.findByKeywordIdAndMemberId(byKeyword.getId(), loginUser.getId()).orElseThrow(() -> new IllegalArgumentException("no mykeyword"));
    }
}
