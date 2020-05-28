package com.palmseung.admin.service;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    public AdminService(MemberRepository memberRepository,
                        PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member createAdmin(Member adminMember) {
        adminMember.changePassword(passwordEncoder.encode(adminMember.getPassword()));

        return memberRepository.save(adminMember);
    }
}
