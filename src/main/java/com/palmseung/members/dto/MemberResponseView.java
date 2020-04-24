package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;
    private MemberRole memberRole;

    @Builder
    public MemberResponseView(Long id, String email, String name, String password, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.memberRole = memberRole;
    }

    public static MemberResponseView of(Member member){
        return MemberResponseView.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .memberRole(member.getMemberRole())
                .build();
    }
}
