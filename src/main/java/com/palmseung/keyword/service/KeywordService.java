package com.palmseung.keyword.service;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.domain.KeywordRepository;
import com.palmseung.keyword.domain.MyKeyword;
import com.palmseung.keyword.domain.MyKeywordRepository;
import com.palmseung.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;

    public MyKeyword create(Member member, Keyword keyword) {
        Keyword savedKeyword = keywordRepository.findByKeyword(keyword.getKeyword())
                .orElseGet(() -> keywordRepository.save(keyword));

        return myKeywordRepository.save(MyKeyword.builder().member(member).keyword(savedKeyword).build());
    }
}
