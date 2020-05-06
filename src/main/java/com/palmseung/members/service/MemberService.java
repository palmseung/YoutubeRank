package com.palmseung.members.service;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.CreateMemberRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.palmseung.common.Messages.*;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyKeywordRepository myKeywordRepository;
    private final KeywordRepository keywordRepository;

    public Member create(CreateMemberRequestView requestView) {
        validateEmail(requestView.getEmail());

        String encodedPassword = passwordEncoder.encode(requestView.getPassword());
        requestView.changePassword(encodedPassword);

        return memberRepository.save(requestView.toEntity());
    }

    public void delete(Member member) {
        memberRepository.delete(findByEmail(member.getEmail()));
    }

    public Member findByEmail(String email) {
        return findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(WARNING_MEMBER_INVALID_MEMBER));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(WARNING_MEMBER_INVALID_MEMBER));
    }

    public Member login(String email, String password) {
        return findMemberByEmail(email)
                .filter(m -> passwordEncoder.matches(password, m.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Transactional
    public Member updateInfo(Member loginUser, Member oldMember, Member updatedMember) {
        updatedMember.updatePassword(passwordEncoder.encode(updatedMember.getPassword()));
        oldMember.update(loginUser, updatedMember);

        return memberRepository.save(oldMember);
    }

    @Transactional
    public MyKeyword addKeyword(Member member, Keyword keyword) {
        Member savedMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new UsernameNotFoundException(member.getEmail()));
        Keyword savedKeyword = saveKeyword(keyword);
        MyKeyword myKeyword = myKeywordRepository.save(buildMyKeyword(member, savedKeyword));
        savedMember.addKeyword(savedKeyword);
        return myKeyword;
    }

    @Transactional
    public List<MyKeyword> findAllKeywords(Member member) {
        return myKeywordRepository.findAllByMemberId(member.getId());
    }

    @Transactional
    public void deleteMyKeywordById(Member loginUser, Long id) {
        MyKeyword myKeyword = myKeywordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(WARNING_MYKEYWORD_INVALID_MYKEYWORD));

        if (!loginUser.equals(myKeyword.getMember())) {
            throw new IllegalArgumentException(WARNING_MYKEYWORD_UNAUTHORIZED_TO_DELETE);
        }

        myKeywordRepository.deleteById(id);
    }

    public Optional<MyKeyword> findMyKeywordById(Long id) {
        return myKeywordRepository.findById(id);
    }

    @Transactional
    public MyKeyword findMyKeywordByMyKeywordId(Member loginUser, Long id) {
        MyKeyword myKeyword = findMyKeywordById(id)
                .orElseThrow(() -> new IllegalArgumentException(WARNING_MYKEYWORD_INVALID_MYKEYWORD));

        if (!loginUser.equals(myKeyword.getMember())) {
            throw new IllegalArgumentException(WARNING_MYKEYWORD_UNAUTHORIZED_TO_READ);
        }

        return myKeyword;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = findByEmail(email);
        return Member.builder()
                .id(member.getId())
                .name(member.getName())
                .email(email)
                .password(member.getPassword())
                .roles(Arrays.asList("ROLE_USER"))
                .build();
    }

    private MyKeyword buildMyKeyword(Member member, Keyword keyword) {
        return MyKeyword.builder()
                .member(member)
                .keyword(keyword)
                .build();
    }

    private Keyword saveKeyword(Keyword keyword) {
        return keywordRepository.findByKeyword(keyword.getKeyword())
                .orElseGet(() -> keywordRepository.save(keyword));
    }

    private void validateEmail(String email) {
        Optional<Member> member = findMemberByEmail(email);

        if (member.isPresent()) {
            throw new IllegalArgumentException(WARNING_MEMBER_EXISTING_EMAIL);
        }
    }

    private Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}