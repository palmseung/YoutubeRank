package com.palmseung.modules.members.dto;

import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
public class MemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    private List<Keyword> keywords = new ArrayList<>();

    @Builder
    public MemberResponseView(Long id, String email, String name, String password,
                              Collection<? extends GrantedAuthority> roles, List<Keyword> keywords) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.keywords = keywords;
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