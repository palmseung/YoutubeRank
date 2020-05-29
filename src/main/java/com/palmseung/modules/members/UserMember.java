package com.palmseung.modules.members;

import com.palmseung.modules.members.domain.Member;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserMember extends User {
    private Member member;

    public UserMember(Member member) {
        super(member.getEmail(), member.getPassword(), member.getAuthorities());
        this.member = member;
    }
}