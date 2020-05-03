package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateMemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;

    @Builder
    public CreateMemberResponseView(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static CreateMemberResponseView of(Member member) {
        return CreateMemberResponseView.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }
}