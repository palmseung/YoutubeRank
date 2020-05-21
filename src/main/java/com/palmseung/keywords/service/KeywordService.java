package com.palmseung.keywords.service;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;

    public MyKeyword findMyKeyword(Member loginUser, String keyword) {
        Keyword savedKeyword = findKeyword(keyword);

        return myKeywordRepository.
                findByKeywordIdAndMemberId(savedKeyword.getId(), loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find MyKeyword!"));
    }

    public List<MyKeyword> findAllMyKeyword(Member loginUser){
        return myKeywordRepository.findAllByMemberId(loginUser.getId());
    }

    public void deleteMyKeyword(Member loginUser, String keyword){
        MyKeyword myKeyword = findMyKeyword(loginUser, keyword);
        myKeywordRepository.deleteById(myKeyword.getId());
    }

    private Keyword findKeyword(String keyword){
        return keywordRepository
                .findByKeyword(keyword)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Keyword!"));
    }
}
