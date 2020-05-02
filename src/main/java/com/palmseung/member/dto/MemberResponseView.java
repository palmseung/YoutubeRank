package com.palmseung.member.dto;

import com.palmseung.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class MemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    public MemberResponseView() {
    }

    @Builder
    public MemberResponseView(Long id, String email, String name, String password,
                              Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public static MemberResponseView of(Member member) {
        return MemberResponseView.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }
}
