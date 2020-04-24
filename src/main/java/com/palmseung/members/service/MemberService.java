package com.palmseung.members.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import com.palmseung.members.domain.MemberRole;
import com.palmseung.members.dto.CreateMemberRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(CreateMemberRequestView requestView) {
        String encodedPassword = passwordEncoder.encode(requestView.getPassword());
        requestView.changePassword(encodedPassword);
        requestView.assginRole(MemberRole.USER);

        return memberRepository.save(requestView.toEntity());
    }
}