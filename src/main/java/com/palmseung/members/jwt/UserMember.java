package com.palmseung.members.jwt;

import com.palmseung.members.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

@Getter
public class UserMember extends User {
    private Member member;

    public UserMember(Member member) {
        super(member.getEmail(), member.getPassword(), member.getAuthorities());
        this.member = member;
    }
}