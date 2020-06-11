package com.palmseung.modules.keywords.service;

import com.palmseung.modules.keywords.domain.MyKeywordRepository;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.keywords.dto.MyKeywordResponseView;
import com.palmseung.modules.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KeywordQueryService {
    private final MyKeywordRepository myKeywordRepository;

    public List<KeywordResponseView> getKeywords(Member loginUser) {
        return myKeywordRepository.findAllByMemberId(loginUser.getId())
                .stream()
                .map(MyKeywordResponseView::of)
                .map(view -> new KeywordResponseView(view.getKeyword()))
                .collect(toList());
    }
}