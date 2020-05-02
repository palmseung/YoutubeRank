package com.palmseung.member.dto;

import com.palmseung.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;
    private List<String> roles = new ArrayList<>();

    public CreateMemberRequestView() {
    }

    @Builder
    public CreateMemberRequestView(String email, String name, String password, List<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public static CreateMemberRequestView of(Member member){
        return CreateMemberRequestView.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }

    public CreateMemberRequestView changePassword(String encodedPassword) {
        this.password = encodedPassword;
        return this;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .roles(roles)
                .build();
    }
}
