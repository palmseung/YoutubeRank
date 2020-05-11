package com.palmseung.members.support;

import com.palmseung.members.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

@Getter
public class UserMember extends User {
    private Member member;

    public UserMember(Member member) {
        super(member.getEmail(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}