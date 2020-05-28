package com.palmseung.members.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.AdminMemberResponseView;
import com.palmseung.members.jwt.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.palmseung.common.support.Messages.WARNING_MEMBER_INVALID_MEMBER;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(Member member) {
        if (findMemberByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.changePassword(encodedPassword);

        return memberRepository.save(member);
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
    public Member updateInfo(Member loginUser, Long id, String password) {
        Member oldMember = findById(id);
        oldMember.updatePassword(loginUser, passwordEncoder.encode(password));
        return oldMember;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = findByEmail(email);
        return new UserMember(member);
    }

    @Transactional(readOnly = true)
    public List<AdminMemberResponseView> getAllMembers() {
        return findAll().stream()
                .map(m -> AdminMemberResponseView.of(m))
                .collect(Collectors.toList());
    }

    private List<Member> findAll() {
        return memberRepository.findAll();
    }

    private Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}