package com.palmseung.keywords.service;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;
    private final MemberRepository memberRepository;

    public MyKeyword addMyKeyword(Member loginUser, String keyword){
        Member savedMember = findMember(loginUser);
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

    public List<MyKeyword> findAllMyKeyword(Member loginUser){
        return myKeywordRepository.findAllByMemberId(loginUser.getId());
    }

    public void deleteMyKeyword(Member loginUser, String keyword){
        MyKeyword myKeyword = findMyKeyword(loginUser, keyword);
        myKeywordRepository.deleteById(myKeyword.getId());
    }

    private Member findMember(Member member){
        return memberRepository
                .findByEmail(member.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(member.getEmail()));
    }

    private Keyword saveKeyword(String keyword){
        return keywordRepository.findByKeyword(keyword)
                .orElseGet(() -> keywordRepository.save(Keyword.of(keyword)));
    }

    private Keyword findKeyword(String keyword){
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
