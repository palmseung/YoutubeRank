package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;

    public CreateMemberRequestView() {
    }

    @Builder
    public CreateMemberRequestView(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static CreateMemberRequestView of(Member member) {
        return CreateMemberRequestView.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}