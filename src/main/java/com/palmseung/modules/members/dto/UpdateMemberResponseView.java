package com.palmseung.modules.members.dto;

import com.palmseung.modules.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateMemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;

    @Builder
    public UpdateMemberResponseView(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static UpdateMemberResponseView of(Member member) {
        return UpdateMemberResponseView.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }
}