package com.palmseung.members.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.domain.MemberRole;
import com.palmseung.members.dto.CreateMemberRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.palmseung.support.Messages.WARNING_MEMBER_EXISTING_EMAIL;
import static com.palmseung.support.Messages.WARNING_MEMBER_INVALID_MEMBER;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(CreateMemberRequestView requestView) {
        validateEmail(requestView.getEmail());

        String encodedPassword = passwordEncoder.encode(requestView.getPassword());
        requestView.changePassword(encodedPassword);
        requestView.assginRole(MemberRole.USER);

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