package com.palmseung.member.service;

import com.palmseung.keyword.domain.Keyword;
import com.palmseung.keyword.service.KeywordService;
import com.palmseung.member.domain.Member;
import com.palmseung.member.domain.MemberRepository;
import com.palmseung.member.dto.CreateMemberRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

import static com.palmseung.support.Messages.WARNING_MEMBER_EXISTING_EMAIL;
import static com.palmseung.support.Messages.WARNING_MEMBER_INVALID_MEMBER;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeywordService keywordService;

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
    public Member updateInfo(Member loginUser, Member updatedMember) {
        updatedMember.updatePassword(passwordEncoder.encode(updatedMember.getPassword()));
        Member oldMember = findById(updatedMember.getId());
        oldMember.update(loginUser, updatedMember);
        return memberRepository.save(oldMember);
    }

    @Transactional
    public Keyword addKeyword(Member member, Keyword keyword) {
        Keyword savedKeyword = keywordService.create(keyword);
        Member savedMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(member.getEmail()));
        savedMember.addKeyword(savedKeyword);
        return savedKeyword;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = findByEmail(email);
        return Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .roles(Arrays.asList("ROLE_USER"))
                .build();
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