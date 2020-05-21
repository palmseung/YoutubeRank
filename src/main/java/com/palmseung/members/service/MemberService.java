package com.palmseung.members.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.dto.CreateMemberRequestView;
import com.palmseung.members.support.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.palmseung.common.Messages.WARNING_MEMBER_EXISTING_EMAIL;
import static com.palmseung.common.Messages.WARNING_MEMBER_INVALID_MEMBER;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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