package com.palmseung.modules.keywords.service;

import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.domain.KeywordRepository;
import com.palmseung.modules.keywords.domain.MyKeyword;
import com.palmseung.modules.keywords.domain.MyKeywordRepository;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;
    private final KeywordQueryService keywordQueryService;

    public MyKeyword addMyKeyword(Member loginUser, String keyword) {
        Keyword savedKeyword = saveKeyword(keyword);

        return saveMyKeyword(loginUser, savedKeyword);
    }

    @Transactional(readOnly = true)
    public MyKeyword findMyKeyword(Member loginUser, String keyword) {
        Keyword savedKeyword = findKeyword(keyword);

        return myKeywordRepository.
                findByKeywordIdAndMemberId(savedKeyword.getId(), loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find MyKeyword!"));
    }

    @Transactional(readOnly = true)
    public List<MyKeyword> findAllMyKeyword(Member loginUser) {
        List<MyKeyword> allByMemberId = myKeywordRepository.findAllByMemberId(loginUser.getId());

        if (allByMemberId != null) {
            return allByMemberId;
        }

        return Collections.emptyList();
    }

    public void deleteMyKeyword(Member loginUser, String keyword) {
        MyKeyword myKeyword = findMyKeyword(loginUser, keyword);
        myKeywordRepository.deleteById(myKeyword.getId());
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseView> getKeywords(Member loginUser) {
        return keywordQueryService.getKeywords(loginUser);
    }

    @Transactional(readOnly = true)
    public List<Keyword> findAll() {
        return keywordRepository.findAll();
    }

    private MyKeyword saveMyKeyword(Member loginUser, Keyword keyword) {
        validateMyKeyword(loginUser, keyword);

        return myKeywordRepository.save(buildMyKeyword(loginUser, keyword));
    }

    private void validateMyKeyword(Member loginUser, Keyword keyword) {
        boolean isRegisteredMyKeyword = myKeywordRepository.findAllByMemberId(loginUser.getId())
                .stream()
                .anyMatch(m -> keyword.getKeyword().equals(m.getStringKeyword()));

        if (isRegisteredMyKeyword) {
            throw new IllegalArgumentException("Already registered!");
        }
    }

    private Keyword saveKeyword(String keyword) {
        return keywordRepository.findByKeyword(keyword)
                .orElseGet(() -> keywordRepository.save(Keyword.of(keyword)));
    }

    private Keyword findKeyword(String keyword) {
        return keywordRepository
                .findByKeyword(keyword)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Keyword!"));
    }

    private MyKeyword buildMyKeyword(Member member, Keyword keyword) {
        return MyKeyword.builder()
                .member(member)
                .keyword(keyword)
                .build();
    }
}