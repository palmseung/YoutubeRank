package com.palmseung.modules.keywords.service;

import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.domain.KeywordRepository;
import com.palmseung.modules.keywords.domain.MyKeyword;
import com.palmseung.modules.keywords.domain.MyKeywordRepository;
import com.palmseung.modules.keywords.dto.KeywordResponseView;
import com.palmseung.modules.keywords.dto.MyKeywordResponseView;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.domain.MemberRepository;
import com.palmseung.modules.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public MyKeyword addMyKeyword(Member loginUser, String keyword) {
        Member savedMember = memberService.findByEmail(loginUser.getEmail());
        Keyword savedKeyword = saveKeyword(keyword);
        savedMember.addKeyword(savedKeyword);

        return myKeywordRepository.save(buildMyKeyword(loginUser, savedKeyword));
    }

    public MyKeyword findMyKeyword(Member loginUser, String keyword) {
        Keyword savedKeyword = findKeyword(keyword);

        return myKeywordRepository.
                findByKeywordIdAndMemberId(savedKeyword.getId(), loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find MyKeyword!"));
    }

    public List<MyKeyword> findAllMyKeyword(Member loginUser) {
        List<MyKeyword> allByMemberId
                = myKeywordRepository.findAllByMemberId(loginUser.getId());

        if (allByMemberId != null) {
            return allByMemberId;
        }

        return Collections.emptyList();
    }

    public void deleteMyKeyword(Member loginUser, String keyword) {
        MyKeyword myKeyword = findMyKeyword(loginUser, keyword);
        myKeywordRepository.deleteById(myKeyword.getId());
    }

    private Member findMember(Member member) {
        return memberRepository
                .findByEmail(member.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(member.getEmail()));
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

    @Transactional(readOnly = true)
    public List<KeywordResponseView> getKeywords(Member loginUser) {
        List<MyKeyword> allMyKeyword = findAllMyKeyword(loginUser);

        if (allMyKeyword.isEmpty()) {
            return Collections.emptyList();
        }

        List<MyKeywordResponseView> myKeywordResponseViews = MyKeywordResponseView.listOf(allMyKeyword);
        return myKeywordResponseViews.stream()
                .map(o -> new KeywordResponseView(o.getKeyword()))
                .collect(Collectors.toList());
    }

    public List<Keyword> findAll() {
        return keywordRepository.findAll();
    }
}